package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.MailDto;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import java.io.FileOutputStream;

@Service
public interface MailService {
    void sendMail(MailDto mailDto);

    void sendMailWithAttachment(String to, String subject, String body, FileOutputStream fileToAttach);
}
