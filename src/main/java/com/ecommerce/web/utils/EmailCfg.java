package com.ecommerce.web.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
public class EmailCfg {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTls;

    Properties properties = new Properties();

    public Properties getProperties() {
        if (properties.isEmpty()) {
            properties.put("mail.smtp.auth", auth);
            properties.put("mail.smtp.starttls.enable", startTls);
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
        }
        return properties;
    }
}