package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Component
public class ApiRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restriction_ID")
    private long apiRestrictionID;

    private String dbName;

    @JoinTable(
            name = "apiRestriction_restriction_piece",
            joinColumns = {@JoinColumn(name = "apiRestrictionID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<RestrictionPiece> restrictionsMap;


    public ApiRestriction() {
        restrictionsMap = new HashSet<>();
    }

    public void setRestrictionsMap(Set<RestrictionPiece> restrictionsMap) {
        this.restrictionsMap.clear();
        if (restrictionsMap != null)
            this.restrictionsMap.addAll(restrictionsMap);
    }

    public void resetRestrictions() {
        restrictionsMap.forEach(restrictionPiece -> restrictionPiece.setCurrent(0));
    }
}
