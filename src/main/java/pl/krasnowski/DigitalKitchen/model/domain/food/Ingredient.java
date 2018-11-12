package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@ToString
@EqualsAndHashCode
@Component
public class Ingredient implements Serializable {

    private static final long serialVersionUID = -307830163024534842L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ingredient_ID;

    private int quantity;

    @Enumerated
    private Unit unit;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food food;

}