package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@EqualsAndHashCode
public class Nutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long nutrient_id;


    @ElementCollection
    @MapKeyColumn(name = "dbName") // column map "key"
    @Column(name = "dbTag") // column map "value"
    private Map<String, String> dbTags;
    @Column
    private String usda_tag, name, notes;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Unit unit;

    public Nutrient(HashMap<String, String> dbTags, String usda_tag, String name, Unit unit, String notes) {
        this.dbTags = dbTags;
        this.usda_tag = usda_tag;
        this.name = name;
        this.notes = notes;
        this.unit = unit;
    }

    public String getUsda_tag() {
        return usda_tag;
    }

    public void setUsda_tag(String usda_tag) {
        this.usda_tag = usda_tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public long getNutrient_id() {
        return nutrient_id;
    }

    public void setNutrient_id(long nutrient_id) {
        this.nutrient_id = nutrient_id;
    }

    public Map<String, String> getDbTags() {
        return dbTags;
    }

    public void setDbTags(Map<String, String> dbTags) {
        this.dbTags = dbTags;
    }

    public String getDetails() {
        return "Nutrient{" +
                "nutrient_id=" + nutrient_id +
                ", dbTags=" + dbTags +
                ", usda_tag='" + usda_tag + '\'' +
                ", nick='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", unit=" + unit +
                '}';
    }

    @Override
    public String toString() {
        return "Nutrient{" + "nick=\'" + name + '\'' + ", unit=" + unit + '}';
    }
}
