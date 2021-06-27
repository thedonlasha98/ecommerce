package com.ecommerce.web.service;

import com.ecommerce.web.model.MailDto;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(MailDto mailDto);

    void sendMailWithAttachment(MailDto mailDto);
}
