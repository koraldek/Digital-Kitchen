package pl.krasnowski.DigitalKitchen.model.domain.kitchen;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@Component
public class Kitchen implements Serializable {

    private static final long serialVersionUID = 7714638958398494852L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long kitchen_ID;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<User> Users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_chef_id")
    private User masterChef;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "kitchen_fk_shoppingList")
    private Set<ShoppingListItem> shoppingList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_fk_kitchenEquipment")
    private Set<KitchensTool> kitchenEquipment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_fk_stock")
    private Set<Consumable> stock;


    /**
     * Stores information of scheduled meals in kitchen: special days of year (birthday's, religion events) or meetings.
     * "period distance" contains information of distance between occurs. 0 value means it will occur only one time.
     */
    @ElementCollection
    @MapKeyColumn(name = "period_distance")
    private Map<DietDay, Integer> plannedDays;


    public void addItemToShoppingList(ShoppingListItem item) {
        shoppingList.add(item);
    }

    public void removeItemFromShoppingList(ShoppingListItem item) {
        shoppingList.remove(item);
    }
}