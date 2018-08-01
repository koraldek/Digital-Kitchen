package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long dishID;

    @Column
    private Timestamp madeDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

    @Column
    private double servingQuantity;

}