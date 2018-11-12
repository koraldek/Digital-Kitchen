package pl.krasnowski.DigitalKitchen.model.events;

import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Set;

@Component
public interface SystemEvent {
    Set<User> getRecipients();

    String getAdditionalMessage();
}
