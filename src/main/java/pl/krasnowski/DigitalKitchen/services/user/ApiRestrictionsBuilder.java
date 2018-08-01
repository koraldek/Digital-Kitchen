package pl.krasnowski.DigitalKitchen.services.user;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.Recipe;
import pl.krasnowski.DigitalKitchen.services.databaseManager.edemam.EdemamManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager;

@Service
public interface ApiRestrictionsBuilder {
    int HOUR = 60;
    int DAY = 24 * HOUR;
    int MONTH = 30 * DAY;

    static ApiRestriction buildFoodApiRestriction(String dbName) {
        if (dbName.equals(NutritionixManager.NUTRITIONIX_DB_NAME)) {
            return buildNutritionixApiRestriction_FOOD();
        } else if (dbName.equals(EdemamManager.EDEMAM_DB_NAME))
            return buildEdemamApiRestriction_FOOD();
        throw new IllegalArgumentException("Excepted existing database name. Received:" + dbName);
    }

    static ApiRestriction buildRecipeApiRestriction(String dbName) {
        if (dbName.equals(EdemamManager.EDEMAM_DB_NAME))
            return buildEdemamApiRestriction_RECIPE();
        throw new IllegalArgumentException("Excepted existing database name. Received:" + dbName);
    }


    private static ApiRestriction buildNutritionixApiRestriction_FOOD() {
        ApiRestriction nApiRestriction = new ApiRestriction();
        nApiRestriction.setDbName(NutritionixManager.NUTRITIONIX_DB_NAME);
        nApiRestriction.setDataType(Food.class);
        nApiRestriction.addPeriod(DAY, 500);
        return nApiRestriction;
    }

    private static ApiRestriction buildEdemamApiRestriction_FOOD() {
        ApiRestriction nApiRestriction = new ApiRestriction();
        nApiRestriction.setDbName(EdemamManager.EDEMAM_DB_NAME);
        nApiRestriction.setDataType(Food.class);
        nApiRestriction.addPeriod(1, 25);
        return nApiRestriction;
    }

    private static ApiRestriction buildEdemamApiRestriction_RECIPE() {
        ApiRestriction nApiRestriction = new ApiRestriction();
        nApiRestriction.setDbName(EdemamManager.EDEMAM_DB_NAME);
        nApiRestriction.setDataType(Recipe.class);
        nApiRestriction.addPeriod(1, 5);
        nApiRestriction.addPeriod(MONTH, 5000);
        return nApiRestriction;
    }
}
