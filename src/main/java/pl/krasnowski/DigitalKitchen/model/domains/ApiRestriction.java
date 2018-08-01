package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class ApiRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int apiRestrictionID;
    @Column
    private String dbName;

    @Column
    Class<?> dataType;


    @JoinTable(
            name = "apiRestriction_restrictionPiece",
            joinColumns = {@JoinColumn(name = "apiRestrictionID")},
            inverseJoinColumns = {@JoinColumn(name = "restrictionPieceID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<RestrictionPiece> restrictionsMap = new HashSet<>();


    public void addPeriod(int period, int max) {
        RestrictionPiece rp = new RestrictionPiece();
        rp.setCurrent(0);
        rp.setMax(max);
        rp.setPeriod(period);
        restrictionsMap.add(rp);
    }


    public void updateRestriction(int current, int period) {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getPeriod() == period)
                rp.setCurrent(current);
        }
    }

    public void incrementRestriction(int period) {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getPeriod() == period)
                rp.setCurrent(rp.getCurrent() + 1);
        }
    }

    public void resetRestriction(int period) {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getPeriod() == period)
                rp.setCurrent(0);
        }
    }

    public void resetAllRestrictions() {
        for (RestrictionPiece rp : restrictionsMap)
            rp.setCurrent(0);
    }

    public int getCurrentValue(int period) {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getPeriod() == period)
                return rp.getCurrent();
        }
        throw new IllegalArgumentException("Method received non existing period value:" + period);
    }

    public int getMaxValue(int period) {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getPeriod() == period)
                return rp.getMax();
        }
        throw new IllegalArgumentException("Method received non existing period value:" + period);
    }

    public boolean isRestrictionReached() {
        for (RestrictionPiece rp : restrictionsMap) {
            if (rp.getCurrent() == rp.getMax())
                return true;
        }
        return false;
    }
}