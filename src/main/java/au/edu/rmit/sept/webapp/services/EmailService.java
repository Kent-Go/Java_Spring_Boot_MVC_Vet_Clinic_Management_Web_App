package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private org.springframework.core.env.Environment env;  // To access properties

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public boolean sendReminderEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // Explicitly set the "From" email address
        String fromAddress = env.getProperty("spring.mail.username");  // Get your email from application.properties
        message.setFrom(fromAddress);

        try {
            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);  // Log success
            return true;
        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());  // Log failure
            return false;
        }
    }
}
