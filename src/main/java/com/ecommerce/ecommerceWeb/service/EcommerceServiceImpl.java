package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.User;
import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.model.UserDto;
import com.ecommerce.ecommerceWeb.ropository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class EcommerceServiceImpl implements EcommerceService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserDto userDto) {
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
        mailDto.setEmail("thedonlasha@gmail.com");
        mailDto.setName("User Registration");
        mailDto.setFeedback("http://localhost:8090/swagger-ui.html#/ecommerce-controller/setPasswordUsingPOST");
        mailService.sendFeedback(mailDto);

    }

    @SneakyThrows
    @Override
    public void setPassword(String password, String rePassword) {
        if (password.equals(rePassword)) {
            User user = userRepository.findById(Long.valueOf(1)).get();
            user.setPassword(passwordEncoder.encode(password));
            user.setStatus("A");
            userRepository.save(user);
        } else {
            throw new Exception("password not equals");
        }
    }
}