package pl.krasnowski.DigitalKitchen.services.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.events.ExpirationDateComesSoon;
import pl.krasnowski.DigitalKitchen.model.events.ExpirationDateReached;

@Component
@Slf4j
public class ExpirationDateListener {

    @Autowired
    NotificationAnnouncer notificationAnnouncer;

    @EventListener
    void incomingExpirationDate(ExpirationDateComesSoon e) {
        String msg = "Incoming expiration date for food:" + e.getItem().getName() +
                ", expiration date:" + "tomorrow..."; //TODO: change to real expiration date of product

        log.debug(msg + ", user to inform:" + e.getRecipient());
        //   notificationAnnouncer.announce(e, e.getRecipient(), msg);
    }

    @EventListener
    void expirationDateReached(ExpirationDateReached e) {
        String msg = "Reached expiration date for food:" + e.getItem().getName() +
                ", expiration date:" + "today!"; //TODO: change to real expiration date of product
        log.debug(msg + ", user to inform:" + e.getRecipient());
        //     notificationAnnouncer.announce(e, e.getRecipient(), msg);
    }
}
