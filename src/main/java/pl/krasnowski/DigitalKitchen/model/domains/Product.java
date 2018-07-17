package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long productID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product parent;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food food;


    /**
     * Expiration date of bought product.
     */
    @Column
    private Timestamp expirationDate;

    @Column
    private int price;

    @Column
    private String barcode;

}