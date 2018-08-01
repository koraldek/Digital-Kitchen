package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domains.Origin;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.ArrayList;
import java.util.function.Function;

import static pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager.NUTRITIONIX_DB_NAME;

interface NutritionixParser {
    Logger log = LoggerFactory.getLogger(NutritionixParser.class);

    static ArrayList<Food> parseDetailedList(InstantSearchDetailedResult instantSearchDetailedResults) {
        ArrayList<Food> result = new ArrayList<>();
        for (IFood f : instantSearchDetailedResults.getFoodInformation())
            result.add(foodParser.apply(f));

        log.debug("Parse executed successfully. Result: \n{}", result.toString());
        return result;
    }

    /**
     * Use only to populate autocomplete list
     *
     * @param instantSearchResult
     * @return list of food for autocomplete list
     */
    static ArrayList<FoodProxy> parseInstantList(InstantSearchResult instantSearchResult) {
        ArrayList<FoodProxy> result = new ArrayList<>();
        for (IFood f : instantSearchResult.getFoodInformation())
            result.add(foodProxyParser.apply(f));

        log.debug("Parse executed successfully. Result: \n{}", result.toString());
        return result;
    }

    /**
     * Parses food from external database.
     *
     * @param searchByCodeResult - Response body from server
     * @return parsed object Food
     */
    static ArrayList<Food> parseSearchByKeywordOrID(SearchByCodeResult searchByCodeResult) {
        ArrayList<Food> result = new ArrayList<>();
        for (IFood f : searchByCodeResult.getFoodInformation())
            result.add(foodParser.apply(f));

        log.debug("Parse executed successfully. Result: \n{}", result.toString());
        return result;
    }

    Function<IFood, FoodProxy> foodProxyParser = (IFood inputObject) -> {
        FoodProxy foodProxy = new FoodProxy();
        foodProxy.setName(inputObject.getName());
        if (StringUtils.isEmpty(inputObject.getPhoto()) || inputObject.getPhoto().equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL))
            foodProxy.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
        else
            foodProxy.setPhoto(inputObject.getPhoto());
        foodProxy.setDbName(NutritionixManager.NUTRITIONIX_DB_NAME);
        foodProxy.setFoodID(inputObject.getFoodID());
        foodProxy.setOrigin(Origin.valueOf(
                inputObject.getClass().getSimpleName().toUpperCase()));

        return foodProxy;
    };

    Function<IFood, Food> foodParser = (IFood inputObject) -> {
        Food food = new Food();
        food.addName("English", inputObject.getName());
        if (StringUtils.isEmpty(inputObject.getPhoto()) || inputObject.getPhoto().equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL))
            food.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
        else
            food.setPhoto(inputObject.getPhoto());

        food.addForeignID(NutritionixManager.NUTRITIONIX_DB_NAME, inputObject.getFoodID());
        for (FullNutrient n : inputObject.getFullNutrients()) {
            if (n.value >= 0.0001) { // ignore ~0 values
                Nutrient nutrient = AppConfig.getNutrientsList().stream().filter( // compare id with one in nutrientsList and convert to Nutrient object
                        nutr -> n.attrId == Long.valueOf(
                                nutr.getDbTags().get(NUTRITIONIX_DB_NAME)))
                        .findFirst().orElse(null);

                food.addNutrient(nutrient, n.value);
            }
        }
        if (inputObject.getAltMeasures() != null) {
            for (AltMeasure am : inputObject.getAltMeasures()) {
                food.addServingSize(am.measure, am.servingWeight);
            }
        }
        food.addServingSize("grams", inputObject.getServingGramsWeight());

        if (inputObject instanceof Branded || inputObject instanceof pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.Food) // ...nutritionix.Food comes from parseSearchByKeywordOrID
            food.addOrigin(Origin.BRANDED);
        else if (inputObject instanceof Common)
            food.addOrigin(Origin.COMMON);

        return food;
    };

}