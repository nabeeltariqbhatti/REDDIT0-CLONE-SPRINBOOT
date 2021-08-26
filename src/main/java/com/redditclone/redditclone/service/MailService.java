package com.redditclone.redditclone.service;


import com.redditclone.redditclone.exceptions.SpringReddiException;
import com.redditclone.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailControllerBuilder mailControllerBuilder;


    @Async
    void sendMail(NotificationEmail notificationEmail) throws SpringReddiException {
        MimeMessagePreparator messagePreparator = mimeMessage ->
        {

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("rclone58@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailControllerBuilder.build(notificationEmail.getBody()));

        };

        try{
            mailSender.send(messagePreparator);
            log.info("Activation email is sent");
        }catch (MailException e){
            throw  new SpringReddiException("Exception occurred while sending email");
        }
    }
}
