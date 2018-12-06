package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@Component
public abstract class Consumable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name ="consumable_ID" )
    protected long consumableID;

    /**
     * Expiration time after diet is opened/created in hours (when hold in proper conditions)
     */
    protected int expTimeAfterOpen;

    @ElementCollection
    @MapKeyColumn(name = "Nutrient_id")
    @Column(name = "quantity")
    protected Map<Nutrient, Double> nutrients = new HashMap<>();

    public void addNutrient(Nutrient nutrient, double value) {
        nutrients.put(nutrient, value);
    }

    public abstract String getName(String language);

    public abstract String getName();
}
