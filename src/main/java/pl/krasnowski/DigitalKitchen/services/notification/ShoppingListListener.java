package pl.krasnowski.DigitalKitchen.services.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.model.events.NewShoppingListItem;
import pl.krasnowski.DigitalKitchen.model.events.ShoppingListItemChangedStatus;

@Service
@Slf4j
public class ShoppingListListener {

    @Autowired
    NotificationAnnouncer notificationAnnouncer;

    @EventListener
    public void shoppingListItemChangedStatus(ShoppingListItemChangedStatus e) {
        String msg = "Shopping list item " + e.getItem().getItem().getName() + ", changed status to: " + e.getItem().getChefDecision() + ". ";
        log.debug(msg);
        if (!StringUtils.isEmpty(e.getAdditionalMessage()))
            msg = msg.concat("Info:" + e.getAdditionalMessage());
        notificationAnnouncer.announce(e, msg);
    }

    @EventListener
    public void newShoppingListItem(NewShoppingListItem e) {
        String msg = "New shopping list item:" + e.getItem().getItem().getName();
        log.debug(msg);
        if (!StringUtils.isEmpty(e.getAdditionalMessage()))
            msg = msg.concat("Info:" + e.getAdditionalMessage());

        notificationAnnouncer.announce(e, msg);
    }
}
