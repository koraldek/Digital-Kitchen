package pl.krasnowski.DigitalKitchen.services.notification;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

@Service
public interface NotificationService {
    void sendNotification(User recipient, String content);
}