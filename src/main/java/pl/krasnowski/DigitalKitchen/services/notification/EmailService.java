package pl.krasnowski.DigitalKitchen.services.notification;

import com.sendgrid.Mail;
import org.springframework.stereotype.Service;

@Service
public interface EmailService extends NotificationService {
    void sendEmail(Mail mail);
}
