package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.Account;
import com.ecommerce.ecommerceWeb.domain.User;
import com.ecommerce.ecommerceWeb.domain.UserAuthorization;
import com.ecommerce.ecommerceWeb.exception.ErrorCode;
import com.ecommerce.ecommerceWeb.exception.GeneralException;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.ropository.AccountRepository;
import com.ecommerce.ecommerceWeb.ropository.UserAuthorizationRepository;
import com.ecommerce.ecommerceWeb.ropository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceTestImpl  {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthorizationRepository userAuthorizationRepository;

    @Autowired
    private AccountRepository accountRepository;
//
//    @Override
//    public void registerUser(UserDto userDto) {
//        List<User> findUserTest =
//        userRepository.findByEmail(userDto.getEmail()).isPresent()
//                ? Collections.singletonList(userRepository.findByEmail(userDto.getEmail()).get())
//                : Collections.emptyList();
//        if (findUserTest.size() > 0) {
//            for (User userTest : findUserTest) {
//                if (userTest.getEmail().equals(userDto.getEmail())) {
//                    throw new GeneralException(ErrorCode.USER_BY_THIS_EMAIL_ALREADY_EXISTS);
//                }
////                if (userTest.getPin().equals(userDto.getPin())) {
////                    throw new GeneralException(ErrorCode.USER_BY_THIS_PIN_ALREADY_EXISTS);
////                }
//            }
//        }
////        UserTest userTest = new UserTest();
////        userTest.setFirstName(userDto.getFirstName());
////        userTest.setLastName(userDto.getLastName());
////        userTest.setPin(userDto.getPin());
////        userTest.setEmail(userDto.getEmail());
////        userTest.setAcctNo(userDto.getAcctNo());
////        userTest.setRegDate(LocalDateTime.now());
////        userTest.setStatus("N");
////        userRepository.save(userTest);
////
////        MailDto mailDto = new MailDto();
////        mailDto.setEmail(userDto.getEmail());
////        mailDto.setSubject("UserTest Registration");
////        mailDto.setBody("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
////        mailService.sendMail(mailDto);
//
//    }
//
//    @Override
//    public void setPassword(String password, String rePassword) {
////        if (password.equals(rePassword)) {
////            UserTest userTest = userRepository.findById(Long.valueOf(1)).get();
////            userTest.setPassword(passwordEncoder.encode(password));
////            userTest.setStatus("A");
////            userRepository.save(userTest);
////        } else {
////            throw new GeneralException(ErrorCode.PASSWORD_NOT_EQUALS);
////        }
//    }
//
//    @Override
//    public void authorization(String email, String password) {
//        password = passwordEncoder.encode(password);
//        User userTest = userRepository.findByEmailAndPassword(email, password);
//        if (userTest.getEmail().equals(email) && userTest.getPassword().equals(password)) {
//            if (!checkAuthorized(userTest.getId())) {
//                UserAuthorization userAuth = new UserAuthorization();
//                userAuth.setUserId(userTest.getId());
//                userAuth.setAuthDate(LocalDateTime.now());
//                userAuth.setStatus("A");
//                userAuthorizationRepository.save(userAuth);
//            } else {
//                UserAuthorization userAuth = userAuthorizationRepository.findFirstByUserIdOrderByAuthDateDesc(userTest.getId());
//                userAuth.setAuthDate(LocalDateTime.now());
//                userAuthorizationRepository.save(userAuth);
//            }
//        } else {
//            throw new GeneralException(ErrorCode.INCORRECT_CREDENTIALS);
//        }
//    }
//
//    @Override
//    public String resetPassword(String pin) {
//        Account userTest = accountRepository.findByPin(pin);
//        if (userTest != null) {
//            MailDto mailDto = new MailDto();
////            mailDto.setEmail(userTest.getEmail());
//            mailDto.setSubject("Reset password");
//            mailDto.setBody("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
//            mailService.sendMail(mailDto);
//
//            return "პაროლის შესაცვლელ ლინკს მიიღებთ მეილზე " + userTest.getUserId();
//        } else {
////            return "პირადი ნომრით " + pin + " მომხმარებელი ვერ მოიძებნა!";
//            throw new GeneralException(ErrorCode.INCORRECT_CREDENTIALS);
//        }
//
//    }
//
//    public boolean checkAuthorized(Long userId) {
//        UserAuthorization userAuth = userAuthorizationRepository.findFirstByUserIdOrderByAuthDateDesc(userId);
//        LocalDateTime now = LocalDateTime.now();
//        boolean isActive = userAuth.getAuthDate().isAfter(now.minusMinutes(30));
//        if (!isActive) {
//            userAuth.setEndDate(now);
//            userAuth.setStatus("D");
//            userAuthorizationRepository.save(userAuth);
//        }
//        return isActive;
//    }

}