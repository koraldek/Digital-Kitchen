package pl.krasnowski.DigitalKitchen.services.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

@Slf4j
@Service
class SMSServiceImpl implements SMSService {

    @Override
    public void sendSMS(String phoneNumber, String content) {
        log.info("Using unimplemented method SMSService to send SMS.");
    }

    @Override
    public void sendNotification(User recipient, String content) {
        sendSMS(recipient.getPhoneNumber(), content);
        log.debug("Sending SMS to user:{}. Content:{}", recipient.getUsername(), content);
    }
}