package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.config.DeveloperBeansConfig;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Origin;
import pl.krasnowski.DigitalKitchen.services.TestConfig;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({AppConfig.class, TestConfig.class, DeveloperBeansConfig.class})
class NutritionixParserTest {

    @Autowired
    private DatabaseManager databaseManager;

    @Autowired
    @Qualifier("nutritionixKeys")
    Map<String, String> nXkeys;

    @Autowired
    @Qualifier("apiRestrictionNutritionix")
    Set<ApiRestriction> apiRestriction;

    @Autowired
    @Qualifier("nxSampleID")
    String nXSampleID;

    @Autowired
    @Qualifier("sampleFoodName")
    String sampleFoodName;

    @Test
    @DisplayName("Check if returned object have photo tag or default image tag,sets ID and have exactly one Origin type - BRANDED OR COMMON.")
    void checkSettingImageAndIDAndOrigin() {
        assertAll(

                () -> {
                    Food food = databaseManager.getFoodByID(nXSampleID, NutritionixManager.NUTRITIONIX_DB_NAME, nXkeys);
                    assertTrue(!StringUtils.isEmpty(food.getPhoto()) || food.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                    assertTrue(food.getOrigin().contains(Origin.BRANDED) || food.getOrigin().contains(Origin.COMMON) && food.getOrigin().size() == 1);
                    assertFalse(food.getForeignID(NutritionixManager.NUTRITIONIX_DB_NAME).isEmpty());
                },
                () -> {
                    for (Food food : databaseManager.getFoodList(sampleFoodName, nXkeys, apiRestriction)) {
                        assertTrue(!StringUtils.isEmpty(food.getPhoto()) || food.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                        assertTrue(food.getOrigin().contains(Origin.BRANDED) || food.getOrigin().contains(Origin.COMMON) && food.getOrigin().size() == 1);
                        assertFalse(food.getForeignID(NutritionixManager.NUTRITIONIX_DB_NAME).isEmpty());
                    }
                },
                () -> {
                    for (FoodProxy proxyFood : databaseManager.getAutocompleteFoodList(sampleFoodName, nXkeys, apiRestriction)) {
                        assertTrue(!StringUtils.isEmpty(proxyFood.getPhoto()) || proxyFood.getPhoto().equals(DatabaseManager.DEFAULT_FOOD_PHOTO_URL));
                        assertTrue(proxyFood.getOrigin() == (Origin.BRANDED) || proxyFood.getOrigin() == (Origin.COMMON));
                        assertFalse(StringUtils.isEmpty(proxyFood.getPhoto()));
                    }
                }
        );
    }
}