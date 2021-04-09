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
    public void sendFeedback(MailDto mailDto) {
        // Create a mail sender


        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailDto.getEmail());
        mailMessage.setTo("lbolga1998@gmail.com");
        mailMessage.setSubject("New mailDto from " + mailDto.getName());
        mailMessage.setText(mailDto.getFeedback());
        // Send mail
        javaMailSender.send(mailMessage);
    }
}
