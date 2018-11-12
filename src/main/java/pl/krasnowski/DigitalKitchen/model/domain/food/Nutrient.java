package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Component
public class Nutrient implements Serializable {

    private static final long serialVersionUID = 7492508723663859976L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrient_id")
    private long nutrientID;


    @ElementCollection
    @MapKeyColumn(name = "dbName") // column map "key"
    @Column(name = "dbTag") // column map "value"
    private Map<String, String> dbTags;
    private String usda_tag, name, notes;

    @Enumerated(EnumType.ORDINAL)
    private Unit unit;

    public Nutrient() {
        dbTags = new HashMap<>();
    }

    public Nutrient(HashMap<String, String> dbTags, String usda_tag, String name, Unit unit, String notes) {
        this.dbTags = dbTags;
        this.usda_tag = usda_tag;
        this.name = name;
        this.notes = notes;
        this.unit = unit;
    }

    public void addTag(String dbName, String tag) {
        dbTags.put(dbName, tag);
    }

}
