package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.food.Origin;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.TestConfig;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManagerImpl;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import({TestConfig.class})
class NutritionixParserTest {


    @Mock
    private DatabaseManagerImpl databaseManager;

    @Autowired
    @Qualifier("nxSampleID")
    String nXSampleID;

    @Autowired
    @Qualifier("sampleFoodName")
    String sampleFoodName;

    @Autowired
    private HashMap<String, String> nutritionixKeys;

    private NutritionixManager nutritionixManager;

    @Mock
    User user;

    @Mock
    UserService uService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(uService.getCurrentUser()).thenReturn(user);
        when(user.getNutritionix_id()).thenReturn(0);

        nutritionixManager = new NutritionixManager(uService, nutritionixKeys);
    }

    @Test
    void checkSettingNutrientsAndServingSizesAndLanguage() {
        Food food = nutritionixManager.getFoodByID("52ae479aaf5a0bb91c015136"); //Cheesecake

        assertAll(
                () -> assertTrue( food.getNutrients().size() >= 4), // It should contains at least value of proteins, carbohydrates, fat and calories
                () -> assertFalse(food.getServingSizes().isEmpty()),
                () -> assertFalse(StringUtils.isEmpty(food.getName("English")))
        );
        System.out.println(food.getName("eng"));
        System.out.println(food.toString());

    }

    @Test
    @org.junit.jupiter.api.Disabled // TODO: temporary disable
    @DisplayName("Check if returned object have photo tag or default image tag,sets ID and have exactly one Origin type - BRANDED OR COMMON.")
    void checkSettingImageAndIDAndOrigin() {
        assertAll(

                () -> {
                    Food food = nutritionixManager.getFoodByID(nXSampleID);
                    assertTrue(!StringUtils.isEmpty(food.getPhoto()) || food.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                    assertTrue(food.getOrigin().contains(Origin.BRANDED) || food.getOrigin().contains(Origin.COMMON) && food.getOrigin().size() == 1);
                    assertFalse(food.getForeignID(DatabaseManager.NUTRITIONIX_DB_NAME).isEmpty());
                },
                () -> {
                    for (Food food : nutritionixManager.getFoodList(sampleFoodName, null)) {
                        assertTrue(!StringUtils.isEmpty(food.getPhoto()) || food.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                        assertTrue(food.getOrigin().contains(Origin.BRANDED) || food.getOrigin().contains(Origin.COMMON) && food.getOrigin().size() == 1);
                        assertFalse(food.getForeignID(DatabaseManager.NUTRITIONIX_DB_NAME).isEmpty());
                    }
                },
                () -> {
                    for (FoodProxy proxyFood : nutritionixManager.getAutocompleteFoodList(sampleFoodName, null)) {
                        assertTrue(!StringUtils.isEmpty(proxyFood.getPhoto()) || proxyFood.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                        assertTrue(proxyFood.getOrigin() == (Origin.BRANDED) || proxyFood.getOrigin() == (Origin.COMMON));
                        assertFalse(StringUtils.isEmpty(proxyFood.getPhoto()));
                    }
                }
        );
    }
}