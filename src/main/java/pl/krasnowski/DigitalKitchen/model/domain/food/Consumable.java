package pl.krasnowski.DigitalKitchen.model.domain.food;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@Component
@JsonDeserialize(as = Food.class)
public abstract class Consumable {

    private static final long serialVersionUID = 9176276121738745661L;


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "consumable_ID")
    protected Long consumableID;

    /**
     * Expiration time after diet is opened/created in hours (when hold in proper conditions)
     */
    protected int expTimeAfterOpen = 0;

    /**
     * Quantity of nutrient in food per 1 gram. If weight of food is not presented ( serving gram), then default
     * serving is used. For example one jar, one pack.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_nutrients",
            joinColumns = @JoinColumn(name = "consumable_ID"))
    protected Set<NutrientWrapper> nutrients;

    public Consumable() {
        this.nutrients = new HashSet<>();
    }

    public void addNutrient(NutrientWrapper nutrientWrapper) {
        nutrients.add(nutrientWrapper);
    }

    public abstract String getName(String language);

    public String getName() {
        Locale locale = LocaleContextHolder.getLocale();
        return getName(locale.getLanguage());
    }
}
