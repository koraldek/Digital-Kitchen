package pl.krasnowski.DigitalKitchen.model.domain.kitchen;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Component
public class ShoppingListItem implements Serializable {

    private static final long serialVersionUID = -5412101052955546549L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shoppingListItemID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe partOfDish;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food item;

    /**
     * 0 - not known, 1 - yes, 2 - no
     */
    private int acceptedByChef = 0;

    private double quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    private User orderedBy;

    String additionalMessage = "";

    public String getChefDecision() {
        if (acceptedByChef == 0)
            return "not known";
        else if (acceptedByChef == 1)
            return "accepted";
        else if (acceptedByChef == 2)
            return "declined";
        else
            throw new IllegalArgumentException("Decision should be number of: 0-not known, 1-accepted or 2-declined");
    }
}