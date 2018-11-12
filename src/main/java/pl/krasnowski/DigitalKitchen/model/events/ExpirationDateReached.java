package pl.krasnowski.DigitalKitchen.model.events;

import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Set;

@Component
public class ExpirationDateReached implements ExpirationDateEvent {

    private transient User user;
    private  transient Consumable food;

    private ExpirationDateReached(User user, Consumable food) {
        this.user = user;
        this.food = food;
    }

    @Override
    public ExpirationDateEvent buildEvent(User user, Consumable food) {
        return new ExpirationDateReached(user, food);
    }

    @Override
    public User getRecipient() {
        return user;
    }

    @Override
    public Consumable getItem() {
        return food;
    }

    @Override
    public Set<User> getRecipients() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAdditionalMessage() {
        throw new UnsupportedOperationException();
    }
}
