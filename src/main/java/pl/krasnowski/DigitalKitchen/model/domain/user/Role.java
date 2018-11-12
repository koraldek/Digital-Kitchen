package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@Component
public class Role {

    private static final long serialVersionUID = -4761997898509589409L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_ID;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

}