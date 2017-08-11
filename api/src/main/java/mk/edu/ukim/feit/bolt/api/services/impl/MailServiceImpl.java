package mk.edu.ukim.feit.bolt.api.services.impl;

import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import mk.edu.ukim.feit.bolt.api.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by gjorgjim on 8/11/17.
 */
@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender mailSender;

    @Value("${app.url}")
    private String APP_URL;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        if(mailSender == null) {
            throw new IllegalArgumentException(JavaMailSender.class.getName() + " cannot be null.");
        }
        this.mailSender = mailSender;
    }

    @Override
    public SimpleMailMessage generatePasswordResetEmail(PasswordResetToken token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(token.getUser().getContact().getEmail());
        message.setSubject(String.format("Password reset request for %s %s", token.getUser().getFirstName(), token.getUser().getLastName()));
        String passwordResetUrl;
        passwordResetUrl = String.format("%sreset?token=%s", APP_URL, token.getToken());
        message.setText(
                String.format("This is an automated message. Please do not reply.\n\n" +
                        "Your password reset link is:\n%s\nThe URL will be valid for 24 hours.\n\nIf you did not request " +
                        "this password change please contact us at contact@bolt.com.\n\n" +
                        "Regards,\n" +
                        "Bolt Messaging team", passwordResetUrl)
        );
        return message;
    }

    @Override
    public void sendPasswordResetEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
