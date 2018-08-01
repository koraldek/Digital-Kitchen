package pl.krasnowski.DigitalKitchen.services.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.Recipe;
import pl.krasnowski.DigitalKitchen.services.databaseManager.LocalDatabaseManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.edemam.EdemamManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class ApiRestrictionsBuilderTest {

    @Test
    void createNxApiRestrictionFood() {
        ApiRestriction ar = ApiRestrictionsBuilder.buildFoodApiRestriction(NutritionixManager.NUTRITIONIX_DB_NAME);
        assertAll(
                () -> assertEquals(ar.getDataType(), Food.class),
                () -> ar.getRestrictionsMap().forEach(rp -> assertEquals(rp.getCurrent(), 0))
        );
    }

    @Test
    void createEdemamApiRestrictionFood() {
        ApiRestriction ar = ApiRestrictionsBuilder.buildFoodApiRestriction(EdemamManager.EDEMAM_DB_NAME);
        assertAll(
                () -> assertEquals(ar.getDataType(), Food.class),
                () -> ar.getRestrictionsMap().forEach(rp -> assertEquals(rp.getCurrent(), 0))
        );
    }

    @Test
    void createEdemamApiRestrictionRecipe() {
        ApiRestriction ar = ApiRestrictionsBuilder.buildRecipeApiRestriction(EdemamManager.EDEMAM_DB_NAME);
        assertAll(
                () -> assertEquals(ar.getDataType(), Recipe.class),
                () -> ar.getRestrictionsMap().forEach(rp -> assertEquals(rp.getCurrent(), 0))
        );
    }

    @Test
    void createLocalApiRestrictionFood() {
        assertThrows(IllegalArgumentException.class, () -> ApiRestrictionsBuilder.buildFoodApiRestriction(LocalDatabaseManager.LOCAL_DB_NAME));
    }

    @Test
    void createXYZApiRestrictionRecipe() {
        assertThrows(IllegalArgumentException.class, () -> ApiRestrictionsBuilder.buildRecipeApiRestriction(NutritionixManager.NUTRITIONIX_DB_NAME));
    }
}