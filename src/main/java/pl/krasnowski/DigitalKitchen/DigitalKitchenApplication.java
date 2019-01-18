package pl.krasnowski.DigitalKitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.DAO.FoodDAO;
import pl.krasnowski.DigitalKitchen.model.DAO.RoleDAO;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietType;
import pl.krasnowski.DigitalKitchen.model.domain.food.*;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.user.*;
import pl.krasnowski.DigitalKitchen.services.diet.DietUtilities;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionService;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


@EnableScheduling
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
@Slf4j
public class DigitalKitchenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalKitchenApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner demoData(FoodDAO foodDAO, UserDAO userDAO, RoleDAO roleDAO, DietUtilities dietUtilities, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            Food food = foodDAO.findByForeignID(DatabaseManager.NUTRITIONIX_DB_NAME, "552c5120f15d04ce1b8c5f00");
            if (food == null) {
                food = new Food(
                        new HashMap<String, String>() {{
                            put(DatabaseManager.NUTRITIONIX_DB_NAME, "552c5120f15d04ce1b8c5f00");
                        }},
                        "https://d1r9wva3zcpswd.cloudfront.net/552c5199251454a554f41f68.jpeg",
                        new HashMap<String, String>() {{
                            put("en", "Healthy snack");
                        }},
                        new HashSet<Origin>() {{
                            add(Origin.BRANDED);
                        }},
                        new HashMap<Unit, Integer>() {{
                            put(Unit.G, 40);
                            put(Unit.PIECE, 12);
                        }},
                        new HashSet<Intolerance>() {{
                            add(Intolerance.GLUTEN);
                            add(Intolerance.PEANUT);
                        }}
                );
                food.setNutrients(new HashSet<NutrientWrapper>() {{
                    add(new NutrientWrapper(Nutrient.ENERGY, 4.5));
                    add(new NutrientWrapper(Nutrient.PROTEIN, 0.05));
                    add(new NutrientWrapper(Nutrient.CARBOHYDRATE, 0.65));
                    add(new NutrientWrapper(Nutrient.FAT, 0.3));
                    add(new NutrientWrapper(Nutrient.CALCIUM, 1));
                    add(new NutrientWrapper(Nutrient.SUGAR, 0.5));
                    add(new NutrientWrapper(Nutrient.FIBER, 0.02));
                    add(new NutrientWrapper(Nutrient.SODIUM, 2));
                    add(new NutrientWrapper(Nutrient.FAT_SATURATED, 0.1));
                    add(new NutrientWrapper(Nutrient.IRON, 0.05));
                }});
                Food finalFood = food;
                foodDAO.save(finalFood);
                log.debug("Added test food by CLR.");
            }
            User newUser = userDAO.findByUsername("q");
            if (newUser == null) {
                Role userRole = roleDAO.findByRoleName("USER");
                int height = 180,
                        weight = 80,
                        age = 35;
                Sex sex = Sex.MALE;
                Diet currentDiet = dietUtilities.computeDiet(DietType.BALANCED, 50, sex, height, weight, age);
                currentDiet.setDietName("Diet for dummy user");

                Kitchen kitchen = new Kitchen();
                kitchen.setName("Kowalsky Kitchen");
                //         kitchen.addItemToStock(new FoodWrapper(0L, food, 50, Unit.G));

                newUser = new User(
                        null,
                        "q",
                        "q",
                        "q@gmail.com",
                        bCryptPasswordEncoder.encode("q"),
                        1,
                        0,
                        "en",
                        UnitSetType.METRIC,
                        "+48555444333",
                        new HashSet<NotificationSetup>(),
                        new HashSet<ApiRestriction>() {{
                            add(ApiRestrictionService.buildNutritionixApiRestriction());
                            add(ApiRestrictionService.buildEdemamApiRestriction());
                        }},
                        new HashSet<Role>() {{
                            add(userRole);
                        }},
                        BodyType.MESOMORPH,
                        Sex.MALE,
                        new Timestamp(ZonedDateTime.of(1990, 5, 25, 11, 23,
                                46, 0, ZoneId.of("UTC")).toInstant().toEpochMilli()),
                        age,
                        height,
                        weight,
                        Silhouette.NORMAL,
                        Silhouette.STRONGMAN,
                        new HashSet<Intolerance>() {{
                            add(Intolerance.GLUTEN);
                            add(Intolerance.PEANUT);
                        }},
                        currentDiet,
                        new ArrayList<Diet>() {{
                            add(currentDiet);
                        }},
                        new HashMap<java.util.Date, HistoryDay>(),
                        new HashSet<FoodPreferention>(),
                        "no photo!",
                        new ArrayList<>(),
                        kitchen,
                        new ArrayList<pl.krasnowski.DigitalKitchen.model.domain.social.Message>(),
                        new ArrayList<Recipe>(),
                        new ArrayList<pl.krasnowski.DigitalKitchen.model.domain.diet.Meal>(),
                        new ArrayList<pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity>()
                );
                currentDiet.setAuthor(newUser);

                userDAO.save(newUser);
                log.debug("Added test user by CLR.");
            }
        };
    }

}