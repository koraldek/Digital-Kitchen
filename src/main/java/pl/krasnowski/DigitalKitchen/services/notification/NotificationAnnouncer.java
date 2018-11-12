package pl.krasnowski.DigitalKitchen.services.notification;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.events.SystemEvent;

/**
 * Checks each user for subscription of particular notification type and source where send notification.
 */
@Service
public interface NotificationAnnouncer {
    void announce(SystemEvent event, String content);
}
