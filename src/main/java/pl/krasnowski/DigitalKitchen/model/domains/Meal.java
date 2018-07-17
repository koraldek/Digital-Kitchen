package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@ToString
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long mealID;
    @Column
    private String name;
    @Column
    private Timestamp mealTime;

    @JoinTable(
            name = "meal_partners",
            joinColumns = {@JoinColumn(name = "mealID")},
            inverseJoinColumns = {@JoinColumn(name = "UserID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> partners;

    @JoinTable(
            name = "meal_dishes",
            joinColumns = {@JoinColumn(name = "mealID")},
            inverseJoinColumns = {@JoinColumn(name = "dishID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Dish> dishes;

    @JoinTable(
            name = "meal_product_dishes",
            joinColumns = {@JoinColumn(name = "mealID")},
            inverseJoinColumns = {@JoinColumn(name = "productID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Product> productDishes;


}