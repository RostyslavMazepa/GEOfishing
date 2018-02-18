package com.geofishing.auth.registration;

import com.geofishing.model.auth.User;
import com.geofishing.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IUserService service;
    @Autowired
    private MessageSource messageSource;
    //@Qualifier("mailSender")
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${email.noreply}")
    private String fromEmail;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final MimeMessage email = constructEmailMessage(event, user, token);
        javaMailSender.send(email);
    }

    private final MimeMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registration/emailConfirm?token=" + token;
        final String message = String.format(messageSource.getMessage("message.mailConfirm", null, event.getLocale()), user.getUsername(), confirmationUrl);
        logger.debug("Message is: " + message);
        MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage());
        try {
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setText(message, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return helper.getMimeMessage();
    }
}
