package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.*;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.util.ArrayList;
import java.util.function.Function;


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
     * Parses diet from external database.
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
        foodProxy.setFoodType("Food");
        return foodProxy;
    };

    Function<IFood, Food> foodParser = (IFood inputObject) -> {
        int servingWeightGrams;
        Food food = new Food();
        log.debug("Parsing food:{}", inputObject.toString());

        /* NAME AND PHOTO */
        food.addName("en", inputObject.getName());
        if (StringUtils.isEmpty(inputObject.getPhoto()) || inputObject.getPhoto().equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL))
            food.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
        else
            food.setPhoto(inputObject.getPhoto());

        /* SERVING SIZES */
        if (inputObject.getAltMeasures() != null)
            for (AltMeasure am : inputObject.getAltMeasures()) {
                food.addServingSize(Unit.getEnum(am.getMeasure()), am.getServingWeight());
            }

        servingWeightGrams = inputObject.getServingWeightGrams();
        if (servingWeightGrams == 0) {
            servingWeightGrams = food.getServingSizes().getOrDefault(Unit.G, 1);
        }

        if (!inputObject.getPrimaryServingMeasure().getMeasure().contains("gram"))
            food.addServingSize(
                    Unit.getEnum(inputObject.getPrimaryServingMeasure().getMeasure()),
                    inputObject.getPrimaryServingMeasure().getQty());

        /* ID */
        food.addForeignID(DatabaseManager.NUTRITIONIX_DB_NAME, inputObject.getFoodID());
        /* NUTRIENTS */
        for (FullNutrient n : inputObject.getFullNutrients()) {
            if (n.getValue() >= 0.0001) { // ignore ~0 values
                Nutrient nutrient = null;
                for (Nutrient nut : Nutrient.values())
                    if (nut.getTag() == n.getAttrId())
                        nutrient = nut;

                if (nutrient == null)
                    throw new IllegalArgumentException("Required nutrient ID=" + n.getAttrId() + " not found in nutrients list.");

                double quantity = Precision.round(n.getValue() / servingWeightGrams, 4);// trim to 4 digits
                food.addNutrient(new NutrientWrapper(nutrient, quantity));
            }
        }
        /* ORIGIN */
        if (inputObject instanceof Branded ||
                inputObject instanceof pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix.Food) // ...nutritionix.Food comes from parseSearchByKeywordOrID
            food.addOrigin(Origin.BRANDED);
        else if (inputObject instanceof Common)
            food.addOrigin(Origin.COMMON);

        return food;
    };

}