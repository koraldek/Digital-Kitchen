package pl.krasnowski.DigitalKitchen.services.databaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface DatabaseManager extends Serializable {
    Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    String DEFAULT_FOOD_PHOTO_URL = "/images/default_food_image.png";

    /**
     * @param keyword
     * @param paramMap
     */
    ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction);

    /**
     * @param keyword
     * @param paramMap
     */
    ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction);

    /**
     * @param barcode scanned barcode
     */
    static Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param FoodID
     * @param dbName
     */
    Food getFoodByID(String FoodID, String dbName, Map<String, String> paramMap);

    /**
     * @param keyword
     * @param paramMap
     */
    List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap);

    /**
     * @param barcode
     */
    Recipe getRecipeByBarcode(int barcode);

    /**
     * @param recipeID
     * @param dbName
     */
    Recipe getRecipeByID(long recipeID, String dbName);

    /**
     * @param keyword
     * @param paramMap Map of additional parameteres: max kcal, food labels
     */
    List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap);
}
