package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class ShoppingListItem {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long shoppingListItemID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Dish partOfDish;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food item;

    /**
     * 0 - not known, 1 - yes, 2 - no
     */
    @Column
    private int acceptedByChief = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    private User orderedBy;

    @ManyToOne(cascade = CascadeType.ALL)
    private Kitchen kitchen;

}