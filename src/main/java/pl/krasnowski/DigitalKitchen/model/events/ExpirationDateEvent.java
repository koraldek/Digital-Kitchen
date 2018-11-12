package pl.krasnowski.DigitalKitchen.model.events;

import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

@Component
public interface ExpirationDateEvent extends SystemEvent {

    ExpirationDateEvent buildEvent(User user, Consumable food);

    User getRecipient();

    Consumable getItem();
}
