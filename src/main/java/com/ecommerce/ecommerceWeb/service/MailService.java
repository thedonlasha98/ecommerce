package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.MailDto;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    public void sendMail(MailDto mailDto);
}
