package pl.krasnowski.DigitalKitchen.model.domain.kitchen;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Component
public class RequiredTool implements Serializable {

    private static final long serialVersionUID = 6140089228398730789L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requiredToolID;

    @ManyToOne(cascade = CascadeType.ALL)
    private KitchensTool kitchenTool;

    /**
     * Estimated required time in minutes of using tool to prepare dish.
     */
    private int requiredTime;

}