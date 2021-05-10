package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.configuration.jwt.JwtUtils;
import com.ecommerce.ecommerceWeb.domain.*;
import com.ecommerce.ecommerceWeb.exception.ErrorCode;
import com.ecommerce.ecommerceWeb.exception.GeneralException;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.request.LoginRequest;
import com.ecommerce.ecommerceWeb.model.request.SignupRequest;
import com.ecommerce.ecommerceWeb.model.response.JwtResponse;
import com.ecommerce.ecommerceWeb.model.response.MessageResponse;
import com.ecommerce.ecommerceWeb.ropository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${hash.generator.alphabet}")
    private String hashGeneratorAlphabet;

    @Value("${hash.generator.length}")
    private int hashGeneratorLength;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordRequestRepository passwordRequestRepository;

    @Autowired
    UserAuthorizationRepository userAuthorizationRepository;

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
    @Transactional
    public ResponseEntity signUp(SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(), null
        );
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);

        Account account = new Account(user.getId(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPin(),
                signUpRequest.getAcctNo(),
                (double) 1000,
                LocalDateTime.now());

        accountRepository.save(account);

        String hash = randomString(hashGeneratorLength);
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setUserId(user.getId());
        passwordRequest.setHashValue(hash);
        passwordRequestRepository.save(passwordRequest);

        MailDto mailDto = new MailDto();
        mailDto.setEmail(user.getEmail());
        mailDto.setSubject("User Registration");
        mailDto.setBody("http://127.0.0.1:5500/passwords/password.html?hash=" + hash);
        mailService.sendMail(mailDto);

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
            throw new GeneralException(ErrorCode.PASSWORD_NOT_EQUALS);
        }
    }

    @Override
    public String resetPassword(String pin) {
        Account account = accountRepository.findByPin(pin);
        if (account != null) {
            MailDto mailDto = new MailDto();
            mailDto.setEmail(account.getUser().getEmail());
            mailDto.setSubject("Reset password");
            mailDto.setBody("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
            mailService.sendMail(mailDto);

            return "პაროლის შესაცვლელ ლინკს მიიღებთ მეილზე " + account.getUser().getEmail();
        } else {
            throw new GeneralException(ErrorCode.INCORRECT_CREDENTIALS);
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
