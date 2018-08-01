package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;


@Entity
@Data
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;

    @NotEmpty(message = "*Please provide your nick")
    private String nickname;

    /**
     * Visible nick for all users along with "nick" property.
     */
    @NotEmpty(message = "*Please provide your Visible nick")
    private String nameVisible;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Length(min = 4, message = "*Your password must have at least 4 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column
    private int active, nutritionix_id, age, height, weight;

    @Column
    private int actualKcal;

    @Column
    private String language;

    @Column
    @Enumerated
    private BodyType bodyType;


    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "roleID")}
    )
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles;

    @JoinTable(
            name = "user_api_restrictions",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "apiRestrictionsID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ApiRestriction> apiRestrictions;

    @ManyToOne(cascade = CascadeType.ALL)
    private Kitchen kitchen;


    @JoinTable(
            name = "user_diets",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "dietID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diet> diets;

    @JoinTable(
            name = "user_friends",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "friendID")}
    )
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> friends;

    @JoinTable(
            name = "user_curr_diet",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "curr_dietID")}
    )
    @ManyToOne(cascade = CascadeType.ALL)
    private Diet currentDiet;

    @ElementCollection(targetClass = Intolerance.class)
    @JoinTable(name = "user_intolerances",
            joinColumns = @JoinColumn(name = "userid"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Intolerance> intolerances;

    @JoinTable(
            name = "user_meal_schedule",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "mealID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Meal> mealSchedule;
}