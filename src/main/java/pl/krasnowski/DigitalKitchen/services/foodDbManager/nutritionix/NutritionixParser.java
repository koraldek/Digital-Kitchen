package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.config.FoodSystemConfig;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domain.food.Origin;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.util.ArrayList;
import java.util.function.Function;

import static pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager.NUTRITIONIX_DB_NAME;


interface NutritionixParser {
    Logger log = LoggerFactory.getLogger(NutritionixParser.class);

    static ArrayList<Food> parseDetailedList(InstantSearchDetailedResult instantSearchDetailedResults) {
        ArrayList<Food> result = new ArrayList<>();
        for (IFood f : instantSearchDetailedResults.getFoodInformation())
            result.add(foodParser.apply(f));

        log.debug("Parse executed successfully. Result:{}", result.toString());
        return result;
    }

    /**
     * Use only to populate autocomplete list
     *
     * @return list of diet for autocomplete list
     */
    static ArrayList<FoodProxy> parseInstantList(InstantSearchResult instantSearchResult) {
        ArrayList<FoodProxy> result = new ArrayList<>();
        for (IFood f : instantSearchResult.getFoodInformation())
            result.add(foodProxyParser.apply(f));

        log.debug("Parse executed successfully. Result:{}", result.toString());
        return result;
    }

    /**
     * Parses food from external database. Use this method for common food (it's name) or with its ID (branded).
     *
     * @param searchByCodeResult - Response body from server
     * @return parsed object Food
     */
    static ArrayList<Food> parseSearchByKeywordOrID(SearchByCodeResult searchByCodeResult) {
        ArrayList<Food> result = new ArrayList<>();
        for (IFood f : searchByCodeResult.getFoodInformation())
            result.add(foodParser.apply(f));

        log.debug("Parse executed successfully. Result:{}", result.toString());
        return result;
    }

    Function<IFood, FoodProxy> foodProxyParser = (IFood inputObject) -> {
        FoodProxy foodProxy = new FoodProxy();
        foodProxy.setName(inputObject.getName());
        if (StringUtils.isEmpty(inputObject.getPhoto()) || inputObject.getPhoto().equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL))
            foodProxy.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
        else
            foodProxy.setPhoto(inputObject.getPhoto());
        foodProxy.setDbName(DatabaseManager.NUTRITIONIX_DB_NAME);
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

        food.addForeignID(DatabaseManager.NUTRITIONIX_DB_NAME, inputObject.getFoodID());
        for (FullNutrient n : inputObject.getFullNutrients()) {
            if (n.value >= 0.0001) { // ignore ~0 values
                Nutrient nutrient = FoodSystemConfig.getNutrientsList().stream().filter( // compare id with one in nutrientsList and convert to Nutrient object
                        nut -> n.attrId == Long.valueOf(
                                nut.getDbTags().get(NUTRITIONIX_DB_NAME)))
                        .findFirst().orElse(null);

                if (nutrient == null)
                    throw new IllegalArgumentException("Required nutrient ID=" + n.attrId + " not found in nutrients list.");

                if (nutrient.getNotes() == null)
                    nutrient.setNotes("");

                food.addNutrient(nutrient, n.value);
            }
        }
        if (inputObject.getAltMeasures() != null) {
            for (AltMeasure am : inputObject.getAltMeasures()) {
                food.addServingSize(am.measure, am.servingWeight);
            }
        }
        food.addServingSize("grams", inputObject.getServingGramsWeight());

        if (inputObject instanceof Branded || inputObject instanceof pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix.Food) // ...nutritionix.Food comes from parseSearchByKeywordOrID
            food.addOrigin(Origin.BRANDED);
        else if (inputObject instanceof Common)
            food.addOrigin(Origin.COMMON);

        return food;
    };

}