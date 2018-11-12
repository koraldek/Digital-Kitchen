package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Component
public class Product extends Consumable implements Serializable {
    private static final long serialVersionUID = -6112656696669254366L;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product parent;

    @ManyToOne(cascade = CascadeType.ALL)
    private Food base;


    /**
     * Ingredients of product. Can be filled by rewriting information from its label.
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
    private int price;
    private String barcode;

    /**
     * Stores names in Map< Language,names> names are comma separated. For example < English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names = new HashMap<>();

    @Override
    public String getName(String lang) {
        if (!StringUtils.isEmpty(names.get(lang)))
            return names.get(lang);
        else if (parent != null && !StringUtils.isEmpty(parent.getName(lang)))
            return parent.getName(lang);
        else if (!StringUtils.isEmpty(base.getName(lang)))
            return base.getName(lang);
        else
            return base.getName("English");
    }

    @Override
    public String getName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usr = Optional.ofNullable((User) auth.getPrincipal());
        String language = usr.<IllegalStateException>orElseThrow(() -> {
            throw new IllegalStateException("Principal object is null.");
        }).getLanguage();
        return getName(language);
    }

}