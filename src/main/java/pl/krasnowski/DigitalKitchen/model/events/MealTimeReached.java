package pl.krasnowski.DigitalKitchen.model.events;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Set;

@Component
@Data
public class MealTimeReached implements MealEvent {
    private transient Set<User> recipients;
    private transient String additionalMessage;
}
