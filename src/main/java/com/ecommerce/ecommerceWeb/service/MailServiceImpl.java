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
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

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
    public void sendMailWithAttachment(String to, String subject, String body, FileOutputStream fileToAttach)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("lbolga1998@gmail.com"));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);

               // File file = new File("E:\\reports\\report.xlsx");


                FileSystemResource file = new FileSystemResource("E:\\reports\\");
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment("report.xlsx", file);
            }
        };

        try {
            javaMailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
