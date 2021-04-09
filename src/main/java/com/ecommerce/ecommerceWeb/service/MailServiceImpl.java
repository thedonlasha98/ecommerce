package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.utils.EmailCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender javaMailSender;
    private final EmailCfg emailCfg;

    public MailServiceImpl(EmailCfg emailCfg) {
        this.emailCfg = emailCfg;
    }
    @Override
    public void sendMail(MailDto mailDto) {
        // Create a mail sender


        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("thedonlasha@gmail.com");
        mailMessage.setTo(mailDto.getEmail());
        mailMessage.setSubject(mailDto.getSubject());
        mailMessage.setText(mailDto.getBody());
        // Send mail
        javaMailSender.send(mailMessage);
    }
}
