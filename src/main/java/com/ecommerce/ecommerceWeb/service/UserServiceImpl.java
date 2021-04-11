package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.User;
import com.ecommerce.ecommerceWeb.domain.UserAuthorization;
import com.ecommerce.ecommerceWeb.exception.ErrorCode;
import com.ecommerce.ecommerceWeb.exception.GeneralException;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.ropository.UserAuthorizationRepository;
import com.ecommerce.ecommerceWeb.ropository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthorizationRepository userAuthorizationRepository;

    @Override
    public void registerUser(UserDto userDto) {
        List<User> findUser = userRepository.findByEmailOrPin(userDto.getEmail(), userDto.getPin());
        if (findUser.size() > 0) {
            for (User user : findUser) {
                if (user.getEmail().equals(userDto.getEmail())) {
                    throw new GeneralException(ErrorCode.USER_BY_THIS_EMAIL_ALREADY_EXISTS);
                }
                if (user.getPin().equals(userDto.getPin())) {
                    throw new GeneralException(ErrorCode.USER_BY_THIS_PIN_ALREADY_EXISTS);
                }
            }
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPin(userDto.getPin());
        user.setEmail(userDto.getEmail());
        user.setAcctNo(userDto.getAcctNo());
        user.setRegDate(LocalDateTime.now());
        user.setStatus("N");
        userRepository.save(user);

        MailDto mailDto = new MailDto();
        mailDto.setEmail(userDto.getEmail());
        mailDto.setSubject("User Registration");
        mailDto.setBody("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
        mailService.sendMail(mailDto);

    }

    @Override
    public void setPassword(String password, String rePassword) {
        if (password.equals(rePassword)) {
            User user = userRepository.findById(Long.valueOf(1)).get();
            user.setPassword(passwordEncoder.encode(password));
            user.setStatus("A");
            userRepository.save(user);
        } else {
            throw new GeneralException(ErrorCode.PASSWORD_NOT_EQUALS);
        }
    }

    @Override
    public void authorization(String email, String password) {
        password = passwordEncoder.encode(password);
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            if (!checkAuthorized(user.getUserId())) {
                UserAuthorization userAuth = new UserAuthorization();
                userAuth.setUserId(user.getUserId());
                userAuth.setAuthDate(LocalDateTime.now());
                userAuth.setStatus("A");
                userAuthorizationRepository.save(userAuth);
            } else {
                UserAuthorization userAuth = userAuthorizationRepository.findFirstByUserIdOrderByAuthDateDesc(user.getUserId());
                userAuth.setAuthDate(LocalDateTime.now());
                userAuthorizationRepository.save(userAuth);
            }
        } else {
            throw new GeneralException(ErrorCode.INCORRECT_CREDENTIALS);
        }
    }

    @Override
    public String resetPassword(String pin) {
        User user = userRepository.findByPin(pin);
        if (user != null) {
            MailDto mailDto = new MailDto();
            mailDto.setEmail(user.getEmail());
            mailDto.setSubject("Reset password");
            mailDto.setBody("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
            mailService.sendMail(mailDto);

            return "პაროლის შესაცვლელ ლინკს მიიღებთ მეილზე " + user.getEmail();
        } else {
//            return "პირადი ნომრით " + pin + " მომხმარებელი ვერ მოიძებნა!";
            throw new GeneralException(ErrorCode.INCORRECT_CREDENTIALS);
        }

    }

    public boolean checkAuthorized(Long userId) {
        UserAuthorization userAuth = userAuthorizationRepository.findFirstByUserIdOrderByAuthDateDesc(userId);
        LocalDateTime now = LocalDateTime.now();
        boolean isActive = userAuth.getAuthDate().isAfter(now.minusMinutes(30));
        if (!isActive) {
            userAuth.setEndDate(now);
            userAuth.setStatus("D");
            userAuthorizationRepository.save(userAuth);
        }
        return isActive;
    }

}