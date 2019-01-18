package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

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
        if (!recipe.getName(language).isEmpty())
            return recipe.getName(language);
        else
            return recipe.getName("en");
    }
}