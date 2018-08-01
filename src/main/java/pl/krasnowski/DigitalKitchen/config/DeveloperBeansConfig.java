package pl.krasnowski.DigitalKitchen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.services.databaseManager.edemam.EdemamManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager;
import pl.krasnowski.DigitalKitchen.services.user.ApiRestrictionsBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class DeveloperBeansConfig {


    @Bean
    public Map<String, String> dummyParamMap() {
        HashMap<String, String> dummyParamMap = new HashMap<>();
        if (dummyParamMap.isEmpty())
            dummyParamMap.put("x-remote-user-id", "0"); // for developing purpose only
        return dummyParamMap;
    }


    /*
    indicates FoodDispatcher on Nx database
     */
    @Bean
    public static Set<ApiRestriction> apiRestrictionNutritionix() {
        Set<ApiRestriction> apiRestrictions = new HashSet<>();
        ApiRestriction edemFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(EdemamManager.EDEMAM_DB_NAME);
        ApiRestriction nXFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(NutritionixManager.NUTRITIONIX_DB_NAME);
        ApiRestriction edemRecipeRestriction = ApiRestrictionsBuilder.buildRecipeApiRestriction(EdemamManager.EDEMAM_DB_NAME);

        edemFoodRestriction.updateRestriction(
                edemFoodRestriction.getMaxValue(1),
                1); // set maximum values to other db's than Nx

        apiRestrictions.add(edemFoodRestriction);
        apiRestrictions.add(nXFoodRestriction);
        apiRestrictions.add(edemRecipeRestriction);
        return apiRestrictions;
    }

    /*
    indicates FoodDispatcher on Edemam database
     */
    @Bean
    public static Set<ApiRestriction> apiRestrictionEdemam() {
        Set<ApiRestriction> apiRestrictions = new HashSet<>();
        ApiRestriction edemFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(EdemamManager.EDEMAM_DB_NAME);
        ApiRestriction nXFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(NutritionixManager.NUTRITIONIX_DB_NAME);
        ApiRestriction edemRecipeRestriction = ApiRestrictionsBuilder.buildRecipeApiRestriction(EdemamManager.EDEMAM_DB_NAME);

        edemFoodRestriction.updateRestriction(
                nXFoodRestriction.getMaxValue(ApiRestrictionsBuilder.DAY),
                ApiRestrictionsBuilder.DAY); // set maximum values to other db's than Edemam

        apiRestrictions.add(edemFoodRestriction);
        apiRestrictions.add(nXFoodRestriction);
        apiRestrictions.add(edemRecipeRestriction);
        return apiRestrictions;
    }

    /**
     * indicates FoodDispatcher on local database
     **/
    @Bean
    public static Set<ApiRestriction> apiRestrictionLocal() {
        Set<ApiRestriction> apiRestrictions = new HashSet<>();
        ApiRestriction edemFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(EdemamManager.EDEMAM_DB_NAME);
        ApiRestriction nXFoodRestriction = ApiRestrictionsBuilder.buildFoodApiRestriction(NutritionixManager.NUTRITIONIX_DB_NAME);
        ApiRestriction edemRecipeRestriction = ApiRestrictionsBuilder.buildRecipeApiRestriction(EdemamManager.EDEMAM_DB_NAME);

        edemFoodRestriction.updateRestriction(
                nXFoodRestriction.getMaxValue(ApiRestrictionsBuilder.DAY),
                ApiRestrictionsBuilder.DAY);
        edemFoodRestriction.updateRestriction(
                edemFoodRestriction.getMaxValue(1),
                1); // set max value for '1 minute' period

        edemRecipeRestriction.updateRestriction(
                edemRecipeRestriction.getMaxValue(1),
                1);

        apiRestrictions.add(edemFoodRestriction);
        apiRestrictions.add(nXFoodRestriction);
        apiRestrictions.add(edemRecipeRestriction);
        return apiRestrictions;
    }


}
