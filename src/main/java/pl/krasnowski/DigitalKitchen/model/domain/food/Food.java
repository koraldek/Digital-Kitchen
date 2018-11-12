package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class Food extends Consumable implements Serializable {
    private static final long serialVersionUID = 1298885643295984186L;

    /**
     * Map which contains IDs from external databases
     * Map<database_name,foreignID>
     */
    @ElementCollection
    @MapKeyColumn(name = "db_Name")
    @Column(name = "foreign_food_id")
    private Map<String, String> foreignIDs = new HashMap<>();

    private String photo;

    /**
     * Stores names in Map<Language,names> names are comma separated. For example <English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names = new HashMap<>();

    @ElementCollection(targetClass = Origin.class)
    @JoinTable(name = "food_origin",
            joinColumns = @JoinColumn(name = "food_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Origin> origin = new HashSet<>();

    /**
     * Array map serving in different units Map<unit_string,int>
     */
    @ElementCollection
    @MapKeyColumn(name = "unit")
    @Column(name = "quantity_serving")
    private Map<String, Integer> servingSizes = new HashMap<>();

    @ElementCollection(targetClass = Intolerance.class)
    @JoinTable(name = "food_intolerances",
            joinColumns = @JoinColumn(name = "consumable_ID"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Intolerance> intolerances = new HashSet<>();

    public void addServingSize(String unit, int size) {
        servingSizes.put(unit, size);
    }

    public String getForeignID(String dbName) {
        return foreignIDs.get(dbName);
    }

    public void addName(String lang, String names) {
        this.names.put(lang, names);
    }

    public void addForeignID(String dbName, String foodID) {
        foreignIDs.put(dbName, foodID);
    }

    public void addOrigin(Origin _origin) {
        origin.add(_origin);
    }

    @Override
    public String getName(String lang) {
        if (!StringUtils.isEmpty(names.get(lang)))
            return names.get(lang);
        else
            return names.get("English");
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