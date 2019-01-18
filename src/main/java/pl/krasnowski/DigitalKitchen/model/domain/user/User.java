package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.social.Message;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}),
                @UniqueConstraint(columnNames = {"email"})
        })
@Entity
@Data
@AllArgsConstructor
public class User {

    /*
                  System general data
    **********************************************************************************************************
             */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id ", nullable = false, insertable = false)
    private Long userID;

    @NotEmpty(message = "*Please provide your username")
    private String username;

    /**
     * Visible username for all users along with "username" property.
     */
    @NotEmpty(message = "*Please provide your visible name")
    private String visibleName;

    //  @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    // @Length(min = 4, message = "*Your password must have at least 4 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    private int active, nutritionix_id;

    private String language;

    private UnitSetType unitSetType;

    private String phoneNumber;


    @JoinTable(
            name = "user_notification_setup",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "notification_setup_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private Set<NotificationSetup> notificationSetup;


    @JoinTable(
            name = "user_api_restrictions",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "restriction_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ApiRestriction> apiRestrictions;

    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "role_ID")}
    )
    @OneToMany(cascade = CascadeType.MERGE)
    private Set<Role> roles;


    /*
                Medical data
        *********************************************************************************************************
     */
    @Enumerated
    private BodyType bodyType;

    @Enumerated
    private Sex sex;

    private Timestamp birthDate;

    private int age;

    private double height, weight;

    private Silhouette currentSilhouette, desiredSilhouette;

    @ElementCollection(targetClass = Intolerance.class)
    @JoinTable(name = "user_intolerances",
            joinColumns = @JoinColumn(name = "user_ID"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Intolerance> intolerances;

    /*
              Diet and physical activity data
  **********************************************************************************************************

     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_diet")
    private Diet currentDiet;

    @JoinTable(
            name = "user_diets",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "diet_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private List<Diet> diets;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "user_history_days")
    @MapKeyTemporal(TemporalType.DATE)
    private Map<Date, HistoryDay> historyDays;


    @JoinTable(
            name = "user_food_preferentions",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "preferention_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FoodPreferention> foodPreferentions;


    /*
                  Community and user own data
 **********************************************************************************************************
     */

    String photo;

    @JoinTable(
            name = "user_friends",
            joinColumns = {@JoinColumn(name = "friend_owner_ID")},
            inverseJoinColumns = {@JoinColumn(name = "friend_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> friends;

    @JoinColumn(name = "kitchen_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Kitchen kitchen;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private List<Message> messages;

    @JoinTable(
            name = "user_saved_recipes",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recipe> savedRecipes;

    @JoinTable(
            name = "user_saved_meals",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "meal_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Meal> savedMeals;

    @JoinTable(
            name = "user_saved_workouts",
            joinColumns = {@JoinColumn(name = "user_ID")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PhysicalActivity> savedWorkouts;

    public User() {
    }

    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public void removeMessage(Message msg) {
        messages.remove(msg);
    }


    public void addHistoryDay(LocalDate _date, HistoryDay _hd) {
        Date date = Date.from(_date.atStartOfDay().toInstant(ZoneOffset.UTC));
        historyDays.put(date, _hd);
    }

    public void removeHistoryDay(LocalDate _date) {
        Date date = Date.from(_date.atStartOfDay().toInstant(ZoneOffset.UTC));
        historyDays.remove(date);
    }

    public HistoryDay getHistoryDay(LocalDate _date) {
        Date date = Date.from(_date.atStartOfDay().toInstant(ZoneOffset.UTC));
        return historyDays.get(date);
    }

    public void addFoodPreferention(FoodPreferention fp) {
        foodPreferentions.add(fp);
    }

    public void removeFoodPreferention(FoodPreferention fp) {
        foodPreferentions.remove(fp);
    }

    //TODO: Add add/remove methods to sets/array lists
}