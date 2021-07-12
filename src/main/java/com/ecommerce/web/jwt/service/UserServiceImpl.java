package com.ecommerce.web.jwt.service;

import com.ecommerce.web.jwt.config.JwtUtils;
import com.ecommerce.web.jwt.domain.*;
import com.ecommerce.web.jwt.repository.*;
import com.ecommerce.web.jwt.request.LoginRequest;
import com.ecommerce.web.jwt.request.SignupRequest;
import com.ecommerce.web.jwt.response.JwtException;
import com.ecommerce.web.jwt.response.JwtResponse;
import com.ecommerce.web.jwt.response.MessageResponse;
import com.ecommerce.web.model.MailDto;
import com.ecommerce.web.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ecommerce.web.jwt.response.JwtMessage.*;

@Service
public class UserServiceImpl implements UserService {

    @Value("${hash.generator.alphabet}")
    private String hashGeneratorAlphabet;

    @Value("${hash.generator.length}")
    private int hashGeneratorLength;

    @Value("${web.front.url}")
    private String webUrl;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final MailService mailService;

    private final PasswordRequestRepository passwordRequestRepository;

    private final UserAuthorizationRepository userAuthorizationRepository;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder encoder, JwtUtils jwtUtils, MailService mailService, PasswordRequestRepository passwordRequestRepository, UserAuthorizationRepository userAuthorizationRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
        this.passwordRequestRepository = passwordRequestRepository;
        this.userAuthorizationRepository = userAuthorizationRepository;
    }

    @Override
    public ResponseEntity signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserAuthorization userAuth = new UserAuthorization(userRepository.getUserByEmail(loginRequest.getEmail()).getId(), LocalDateTime.now(), jwt);
        userAuthorizationRepository.save(userAuth);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity signUp(SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(EMAIL_IS_ALREADY_IN_USE.getDesc()));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(), null
        );
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new JwtException(ROLE_IS_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new JwtException(ROLE_IS_NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.MODERATOR)
                                .orElseThrow(() -> new JwtException(ROLE_IS_NOT_FOUND));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new JwtException(ROLE_IS_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);

        BigDecimal defaultBalance = BigDecimal.valueOf(1000);

        Account account = new Account(user.getId(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPin(),
                signUpRequest.getAcctNo(),
                defaultBalance,
                LocalDateTime.now());

        accountRepository.save(account);

        String hash = randomString(hashGeneratorLength);
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setUserId(user.getId());
        passwordRequest.setHashValue(hash);
        passwordRequestRepository.save(passwordRequest);

        mailService.sendMail(MailDto.builder()
                .email(user.getEmail())
                .subject("User Registration")
                .body(webUrl + "passwords/password.html?hash=" + hash)
                .build());

        return ResponseEntity.ok(user.getId());
    }

    @Override
    public String setPassword(String hash, String password, String rePassword) {
        if (password.equals(rePassword)) {
            Long userId = passwordRequestRepository.getPasswordRequestByHashValue(hash).getUserId();
            User user = userRepository.findById(userId).get();
            user.setPassword(encoder.encode(password));
            userRepository.save(user);

            return "თქვენ წარამტებით დააყენეთ პაროლი!";
        } else {
            throw new JwtException(PASSWORD_NOT_EQUALS);
        }
    }

    @Override
    public String resetPassword(String pin) {
        Account account = accountRepository.findByPin(pin);
        if (account != null) {

            String hash = randomString(hashGeneratorLength);
            PasswordRequest passwordRequest = new PasswordRequest();
            passwordRequest.setUserId(account.getUserId());
            passwordRequest.setHashValue(hash);
            passwordRequestRepository.save(passwordRequest);

            MailDto mailDto = new MailDto();
            mailDto.setEmail(account.getUser().getEmail());
            mailDto.setSubject("Reset password");
            mailDto.setBody(webUrl + "passwords/password.html?hash=" + hash);
            mailService.sendMail(mailDto);

            return "პაროლის შესაცვლელ ლინკს მიიღებთ მეილზე " + account.getUser().getEmail();
        } else {
            throw new JwtException(INCORRECT_CREDENTIALS);
        }

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    private String randomString(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(hashGeneratorAlphabet.charAt(rnd.nextInt(hashGeneratorAlphabet.length())));
        }
        return sb.toString();
    }
}
