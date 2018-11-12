package pl.krasnowski.DigitalKitchen.model.events;

import pl.krasnowski.DigitalKitchen.services.SocialService;
import pl.krasnowski.DigitalKitchen.services.notification.EmailService;
import pl.krasnowski.DigitalKitchen.services.notification.SMSService;

public enum NotificationDestination {
    message(SocialService.class),
    sms(SMSService.class),
    email(EmailService.class);

    private final Class destination;

    NotificationDestination(Class<?> serviceClass) {
        this.destination = serviceClass;
    }

    public Class getDestination() {
        return destination;
    }
}
