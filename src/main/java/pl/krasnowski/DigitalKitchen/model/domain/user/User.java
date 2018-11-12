package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.social.Message;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Entity
@Data
public class User implements Serializable {

    /*
                  System general data
    **********************************************************************************************************
             */
    private static final long serialVersionUID = -6684258566034441672L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id ")
    private Long userID;

    //  @NotEmpty(message = "*Please provide your username")
    private String username;

    /**
     * Visible username for all users along with "username" property.
     */
    //   @NotEmpty(message = "*Please provide your visible name")
    private String visibleName;

    //  @Email(message = "*Please provide a valid Email")
    //  @NotEmpty(message = "*Please provide an email")
    private String email;

    // @Length(min = 4, message = "*Your password must have at least 4 characters")
    //   @NotEmpty(message = "*Please provide your password")
    private String password;

    private int active, nutritionix_id;

    private String language;

    private UnitSetType unitSetType;

    private String phoneNumber;


    @JoinTable(
            name = "user_notification_setup",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "notification_setup_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private Set<NotificationSetup> notificationSetup;


    @JoinTable(
            name = "user_api_restrictions",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "restriction_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ApiRestriction> apiRestrictions;

    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "role_ID")}
    )
    @ManyToMany
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
            joinColumns = @JoinColumn(name = "userID"))
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
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "diet_ID")}
    )
    @OneToMany
    private List<Diet> diets;


    @ElementCollection
    @MapKeyColumn(name = "history_day_date")
    @Column(name = "user_history_days")
    private Map<LocalDate, HistoryDay> historyDays;


    @JoinTable(
            name = "user_food_preferentions",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "preferention_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY)
    private Set<FoodPreferention> foodPreferentions;


    /*
                  Community and user own data
 **********************************************************************************************************
     */
    @JoinTable(
            name = "user_friends",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "friend_ID")}
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> friends;

    @JoinTable(name = "kitchen_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Kitchen kitchen;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_ID")
    private List<Message> messages;

    @JoinTable(
            name = "user_saved_recipes",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY)
    private List<Recipe> savedRecipes;

    @JoinTable(
            name = "user_saved_meals",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "meal_ID")}
    )
    @OneToMany(fetch = FetchType.LAZY)
    private List<Meal> savedMeals;

    @JoinTable(
            name = "user_saved_workouts",
            joinColumns = {@JoinColumn(name = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    @OneToMany(fetch = FetchType.LAZY)
    private List<PhysicalActivity> savedWorkouts;


    public User() {
        apiRestrictions = new HashSet<>();
        diets = new ArrayList<>();
        messages = new ArrayList<>();
        friends = new ArrayList<>();
        intolerances = new HashSet<>();
        historyDays = new HashMap<>();
        foodPreferentions = new HashSet<>();
        savedMeals = new ArrayList<>();
        savedRecipes = new ArrayList<>();
        savedWorkouts = new ArrayList<>();
    }


    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public void removeMessage(Message msg) {
        messages.remove(msg);
    }


    public void addHistoryDay(LocalDate date, HistoryDay hd) {
        historyDays.put(date, hd);
    }

    public void removeHistoryDay(LocalDate date) {
        historyDays.remove(date);
    }

    public HistoryDay getHistoryDay(LocalDate date) {
        return historyDays.get(date);
    }

    public void addFoodPreferention(FoodPreferention fp) {
        foodPreferentions.add(fp);
    }

    public void removeFoodPreferention(FoodPreferention fp) {
        foodPreferentions.remove(fp);
    }
}