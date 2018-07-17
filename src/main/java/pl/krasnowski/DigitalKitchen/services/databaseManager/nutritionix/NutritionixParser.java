package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domains.Origin;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.ArrayList;

import static pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager.NUTRITIONIX_DB_NAME;

@Component
interface NutritionixParser {
    Logger log = LoggerFactory.getLogger(NutritionixParser.class);

    static ArrayList<Food> parseDetailedList(InstantSearchDetailedResult instantSearchDetailedResults) {
        ArrayList<Food> result = new ArrayList<>();

        for (Common c : instantSearchDetailedResults.getCommon()) {
            Food newFood = new Food();

            newFood.addName("English", c.foodName);
            newFood.setPhoto(c.photo.thumb);
            newFood.addServingSize("grams", c.servingWeightGrams);
            newFood.addServingSize(c.servingUnit, c.servingQty);
            for (FullNutrient n : c.fullNutrients) {

                if (n.value >= 0.0001) {
                    Nutrient nutrient = AppConfig.getNutrientsList().stream().filter( // compare id with id in nutrientsList and convert to Nutrient object
                            nutr -> n.attrId == Long.valueOf(
                                    nutr.getDbTags().get(NUTRITIONIX_DB_NAME)))
                            .findFirst().orElse(null);

                    newFood.addNutrient(nutrient, n.value);
                }
            }
            newFood.addOrigin(Origin.valueOf("COMMON"));
            result.add(newFood);
        }

        for (Branded b : instantSearchDetailedResults.getBranded()) {
            Food newFood = new Food();

            newFood.addName("English", b.foodName);
            newFood.setPhoto(b.photo.thumb);
            newFood.addServingSize("grams", b.servingWeightGrams);
            newFood.addServingSize(b.servingUnit, b.servingQty);
            for (FullNutrient n : b.fullNutrients) {
                if (n.value >= 0.0001) {
                    Nutrient nutrient = AppConfig.getNutrientsList().stream().filter(
                            nutr -> n.attrId == Long.valueOf(
                                    nutr.getDbTags().get(NUTRITIONIX_DB_NAME)))
                            .findFirst().orElse(null);

                    newFood.addNutrient(nutrient, n.value);
                }
            }
            newFood.addOrigin(Origin.BRANDED);
            result.add(newFood);
        }
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

        for (Common c : instantSearchResult.getCommon()) {
            log.debug("Common to parse:" + c.toString());
            FoodProxy newFood = new FoodProxy();
            if (c.photo.thumb == null) {
                newFood.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
            } else {
                if (c.photo.thumb.equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL) || (c.photo.thumb.equals("")))
                    newFood.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
                else
                    newFood.setPhoto(c.photo.thumb);
            }

            newFood.setFoodID(c.foodName);
            newFood.setName(c.foodName);
            newFood.setOrigin(Origin.COMMON); // set flag for type of food in case of different ways of getting details in future requests
            result.add(newFood);
        }

        for (Branded b : instantSearchResult.getBranded()) {
            FoodProxy newFood = new FoodProxy();
            newFood.setName(b.foodName);
            if (b.photo.thumb == null) {
                newFood.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
            } else {
                if (b.photo.thumb.equals(NutritionixManager.DEFAULT_EXTERNAL_PHOTO_URL) || (b.photo.thumb.equals("")))
                    newFood.setPhoto(DatabaseManager.DEFAULT_FOOD_PHOTO_URL);
                else
                    newFood.setPhoto(b.photo.thumb);
            }

            newFood.setFoodID(b.nixItemId);
            newFood.setOrigin(Origin.BRANDED); // set flag for type of food in case of different ways of getting details in future requests
            result.add(newFood);
        }
        log.debug("Parse executed successfully. Result: \n{}", result.toString());
        return result;
    }

    /**
     * Parses food from external database.
     *
     * @param foodItem - object from JSON
     * @return parsed object Food
     */
    static Food parseResult(pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.Food foodItem) {
        Food newFood = new Food();

        newFood.addName("English", foodItem.foodName);
        newFood.setPhoto(foodItem.photo.thumb);
        newFood.addServingSize("grams", foodItem.servingWeightGrams);
        for (AltMeasure measure : foodItem.altMeasures)
            newFood.addServingSize(measure.measure, measure.servingWeight);
        for (FullNutrient n : foodItem.fullNutrients) {
            if (n.value >= 0.0001) { // ignore 0 values
                Nutrient nutrient = AppConfig.getNutrientsList().stream().filter( // compare id with id in nutrientsList and convert to Nutrient object
                        nutr -> n.attrId == Long.valueOf(
                                nutr.getDbTags().get(NUTRITIONIX_DB_NAME)))
                        .findFirst().orElse(null);

                newFood.addNutrient(nutrient, n.value);
            }
        }
        if (foodItem.nixItemId == null || foodItem.nixItemId.equals("null"))
            newFood.addOrigin(Origin.COMMON);
        else
            newFood.addOrigin(Origin.BRANDED);

        log.debug("Parse executed successfully. Result: \n{}", newFood.toString());
        return newFood;
    }
}