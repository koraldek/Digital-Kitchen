package pl.krasnowski.DigitalKitchen.model.domain.food;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.serialization.NutrientWrapperDeserializer;

import javax.persistence.*;

@Data
@Component
@NoArgsConstructor
@Table(name = "nutrient_wrapper")
@Entity
@JsonDeserialize(using = NutrientWrapperDeserializer.class)
public class NutrientWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrient_wrapper_ID")
    private long nutrientWrapperID;

    @Enumerated
    private Nutrient nutrient;
    /**
     * quantity per unit of nutrient
     */
    private double quantity;


    public NutrientWrapper(Nutrient nutrient, double quantity) {
        this.nutrient = nutrient;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "NutrientWrapper{" +
                "ID=" + nutrientWrapperID +
                ", nutrient=" + nutrient +
                ", qt=" + quantity +
                '}';
    }
}
