package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long ingredientID;

    @Column
    private int quantity;

    @Column
    @Enumerated
    private Unit unit;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food food;

}