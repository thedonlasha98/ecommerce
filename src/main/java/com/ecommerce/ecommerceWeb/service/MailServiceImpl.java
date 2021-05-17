package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.model.MailDto;
import com.ecommerce.ecommerceWeb.utils.EmailCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.Properties;

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

    @Override
    public void sendMailWithAttachment(MailDto mailDto)
    {
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", emailCfg.getHost());
            props.put("mail.smtp.port", emailCfg.getPort());

            Session session = Session.getInstance(props, new javax.mail.Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(emailCfg.getUsername(), emailCfg.getPassword());
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("thedonlasha@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailDto.getEmail()));
            msg.setSubject(mailDto.getSubject());
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mailDto.getBody(),
                    "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(mailDto.getFileAttach());
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);
            Transport.send(msg);
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
    }
}
