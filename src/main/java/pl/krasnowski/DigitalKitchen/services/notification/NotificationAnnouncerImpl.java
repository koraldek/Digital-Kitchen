package pl.krasnowski.DigitalKitchen.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.NotificationSetup;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.events.SystemEvent;
import pl.krasnowski.DigitalKitchen.services.SocialService;

@Service
public class NotificationAnnouncerImpl implements NotificationAnnouncer {

    @Autowired
    EmailService emailService;
    @Autowired
    SMSService smsService;
    @Autowired
    SocialService socialService;

    @Override
    public void announce(SystemEvent event, String content) {
        for (User u : event.getRecipients())
            for (NotificationSetup ns : u.getNotificationSetup())
                if (ns.getNotificationSource().getEventType().equals(event.getClass())) {

                    ns.getNotificationDestination().forEach(dest -> {

                        if (dest.getDestination().equals(SocialService.class))
                            socialService.sendNotification(u, content);
                        else if (dest.getDestination().equals(EmailService.class))
                            emailService.sendNotification(u, content);
                        else if (dest.getDestination().equals(SMSService.class))
                            smsService.sendNotification(u, content);
                    });
                    return;
                }
    }
}