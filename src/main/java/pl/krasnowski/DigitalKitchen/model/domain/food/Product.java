package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class Product extends Consumable implements Serializable {
    private static final long serialVersionUID = -6112656696669254366L;

    @OneToOne(cascade = CascadeType.ALL)
    private Product parent;

    @OneToOne
    private Food base;

    /**
     * Ingredients of product. Filled by rewriting information from its label.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Product_ingredients",
            joinColumns = {@JoinColumn(name = "consumable_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_ID")}
    )
    private List<Ingredient> ingredients;


    /**
     * Expiration date of bought product.
     */
    private Timestamp expirationDate;

    /**
     * name of shop where product was bought
     */
    private String provider;
    private String barcode;
    private int price;

    /**
     * Stores names in Map< Language,names> names are comma separated. For example < English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names = new HashMap<>();

    public void addName(String lang, String names) {
        this.names.put(lang, names);
    }

    @Override
    public String getName(String lang) {
        if (!StringUtils.isEmpty(names.get(lang)))
            return names.get(lang);
        else if (parent != null && !StringUtils.isEmpty(parent.getName(lang)))
            return parent.getName(lang);
        else if (!StringUtils.isEmpty(base.getName(lang)))
            return base.getName(lang);
        else
            return base.getName("en");
    }

    public Set<NutrientWrapper> getNutrients() {
        if (this.nutrients.isEmpty())
            return base.getNutrients();
        else
            return this.nutrients;
    }

    public int getExpirationAfterOpen() {
        if (this.expTimeAfterOpen == 0)
            return base.expTimeAfterOpen;
        else
            return this.expTimeAfterOpen;
    }

}