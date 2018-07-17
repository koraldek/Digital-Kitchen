package pl.krasnowski.DigitalKitchen.services.databaseManager;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.services.databaseManager.edemam.EdemamManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix.NutritionixManager;

import java.util.ArrayList;
import java.util.Map;

interface FoodDispatcher {

    Logger log = LoggerFactory.getLogger(FoodDispatcher.class);


    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction) throws UnirestException {
        log.debug("Running with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);

        return NutritionixManager.getAutocompleteFoodList(keyword, paramMap);
    }


    /**
     * @param barcode
     */
    static Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param foodID
     * @param dbName
     */
    static Food getFoodByID(String foodID, String dbName, Map<String, String> paramMap) throws IllegalArgumentException, UnirestException {
        log.debug("Running Database manager to food with ID:{}. Choosed database:{}, parameters:{}", foodID, dbName, paramMap);

        switch (dbName) {
            case LocalDatabaseManager.LOCAL_DB_NAME: {
                return LocalDatabaseManager.getFoodByID(Long.valueOf(foodID));
            }
            case NutritionixManager.NUTRITIONIX_DB_NAME: {
                return NutritionixManager.getFoodByID(foodID, paramMap);
            }
            case EdemamManager.EDEMAM_DB_NAME: {
                return EdemamManager.getFoodByID(foodID);
            }
            default:
                throw new IllegalArgumentException("Unknown database name. dbName argument:" + dbName);
        }
    }

    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction) throws UnirestException {
        log.debug("Running Database manager to food with keyword:{}, parameters:{}", keyword, paramMap);

        return NutritionixManager.getFoodList(keyword, paramMap);
    }

}