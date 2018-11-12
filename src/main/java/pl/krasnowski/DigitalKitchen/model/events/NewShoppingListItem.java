package pl.krasnowski.DigitalKitchen.model.events;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.ShoppingListItem;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Set;

@Component
@Data
public class NewShoppingListItem implements ShoppingListItemEvent {
    private transient ShoppingListItem item;
    private transient Set<User> recipients;
    private transient String additionalMessage;

    public NewShoppingListItem(ShoppingListItem item, Set<User> recipients, String additionalMessage) {
        this.item = item;
        this.recipients = recipients;
        this.additionalMessage = additionalMessage;
    }

    public NewShoppingListItem() {
    }

    @Override
    public String getAdditionalMessage() {
        return additionalMessage;
    }
}
