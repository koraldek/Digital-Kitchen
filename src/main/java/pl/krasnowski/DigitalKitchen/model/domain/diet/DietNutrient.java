package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;

import javax.persistence.*;

/**
 * Composition of data used to determine how much and in which period user should consume particular nutrient.
 */
@Entity
@Data
@Component
public class DietNutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_nutrient_id")
    private long dietNutrientID;

    @JoinTable(name = "nutrient_ID")
    private Nutrient nutrient;

    /**
     * Period of foodAndWorkoutDiary in days.
     */
    private int period = 1;

    /**
     * Amount of nutrient in grams.
     */
    private double value;

}
