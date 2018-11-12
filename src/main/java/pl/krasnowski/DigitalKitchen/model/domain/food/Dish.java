package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class Dish extends Consumable {

    private double servingQuantity;

    private Timestamp madeDate;

    @JoinColumn(name = "recipe")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Recipe recipe;

    private String description;

    @Override
    public String getName(String language) {
        return recipe.getName(language);
    }

    @Override
    public String getName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usr = Optional.ofNullable((User) auth.getPrincipal());
        String language = usr.<IllegalStateException>orElseThrow(() -> {
            throw new IllegalStateException("Principal object is null.");
        }).getLanguage();
        return getName(language);
    }
}