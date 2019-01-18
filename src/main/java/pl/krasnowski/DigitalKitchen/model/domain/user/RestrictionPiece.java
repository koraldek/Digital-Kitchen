package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Component
@Table(name = "api_restriction_piece")
@NoArgsConstructor
public class RestrictionPiece implements Serializable {

    private static final long serialVersionUID = -6495061787761597899L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restriction_piece_ID")
    private long restrictionPieceID;

    private int period, current, max, queryType;

    Class<?> dataType;

    public RestrictionPiece(int period, int max, Class<?> dataType, int queryType) {
        this.current = 0;
        this.period = period;
        this.max = max;
        this.queryType = queryType;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "RestrictionPiece{" +
                "ID=" + restrictionPieceID +
                ", period=" + period +
                ", current=" + current +
                ", max=" + max +
                ", queryType=" + queryType +
                ", dataType=" + dataType.getSimpleName() +
                '}';
    }
}
