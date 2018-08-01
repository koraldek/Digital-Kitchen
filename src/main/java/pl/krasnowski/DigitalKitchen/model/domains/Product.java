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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long productID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product parent;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food base;


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