package mk.edu.ukim.feit.bolt.api.services;

import mk.edu.ukim.feit.bolt.api.models.PasswordResetToken;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by gjorgjim on 8/11/17.
 */
public interface MailService {
    SimpleMailMessage generatePasswordResetEmail(PasswordResetToken token);
    void sendPasswordResetEmail(SimpleMailMessage message);
}
