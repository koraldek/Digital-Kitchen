package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Wrapper for implementation of Consumable real objects. Contains Consumable and quantity of it.
 */
@Entity
@Data
@Component
@NoArgsConstructor
public class FoodWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_wrapper_ID",nullable = false,updatable = false)
    private long foodWrapperID;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "consumable_id")
    private Consumable food;

    private double quantity;

    @Enumerated
    private Unit unit;

    @Override
    public String toString() {
        return "FoodWrapper{" +
                "ID=" + foodWrapperID +
                ", food=" + food.getName() +
                ", qt=" + quantity +
                ", unit=" + unit +
                '}';
    }

    public void addQuantity(double quantity) {
        this.quantity = this.quantity + quantity;
    }

    public void substractQuantity(double quantity) {
        this.quantity = this.quantity - quantity;
    }

}
