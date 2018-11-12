package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Domain of feature to be included in future release.
 */
@Data
@Component
@Entity
public class FoodPreferention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_Preferention_ID")
    private long foodPreferentionID;
    /**
     * Hold information about diet/recipe how much user likes it. Range is 1 to 5. 1- "I don't want to see it", 5-"I love it!"
     */
    @Min(1)
    @Max(5)
    private int stars;

    /**
     * How often user like to eat this. Delay time in days.
     */
    @Min(-1)
    private int consumeDelay;

    /**
     * It can be either Recipe(as concrete dish),diet and product.
     */
    @JoinColumn
    @OneToOne
    private Consumable consumable;

}
