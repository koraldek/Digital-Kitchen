package pl.krasnowski.DigitalKitchen.services.notification;

import org.springframework.stereotype.Service;

@Service
public interface SMSService extends NotificationService {
    void sendSMS(String phoneNumber, String content);
}