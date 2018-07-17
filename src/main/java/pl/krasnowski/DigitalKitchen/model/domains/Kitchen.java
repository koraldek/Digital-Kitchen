package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kitchenID;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "kitchen_fk_users")
    private Set<User> Users;

    @ManyToOne(cascade = CascadeType.ALL)
    private User MasterChief;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "kitchen_fk_shoppingList")
    private List<ShoppingListItem> shoppingList;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_fk_kitchenEquipment")
    private Set<KitchensTool> kitchenEquipment;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_fk_stock")
    private Set<Product> stock;

}