package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.RequiredTool;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Component
public class Recipe extends Consumable implements Serializable {
    private static final long serialVersionUID = -6311879423730238072L;

    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = {@JoinColumn(name = "recipeID")},
            inverseJoinColumns = {@JoinColumn(name = "ingredientsID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    @JoinTable(
            name = "recipe_tools",
            joinColumns = {@JoinColumn(name = "mealID")},
            inverseJoinColumns = {@JoinColumn(name = "requiredToolID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequiredTool> requiredTools;

    /**
     * Estimated prepare time in minutes.
     */
    private int PrepareTime, totalTime, servings;

    @ElementCollection
    @MapKeyColumn(name = "number")
    @Column(name = "description")
    private Map<Integer, String> instructions;


    /**
     * Stores names in Map<Language,names> names are comma separated. For example <English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names;

    @Override
    public String getName(String language) {
        return names.get(language);
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