package pl.krasnowski.DigitalKitchen.services.databaseManager;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface DatabaseManager extends Serializable {

    String DEFAULT_FOOD_PHOTO_URL = "/images/default_food_image.png";

    /**
     * @param keyword
     * @param paramMap
     */
    ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap, Set<ApiRestriction> apiRestrictions);

    /**
     * @param keyword
     * @param paramMap
     */
    ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap, Set<ApiRestriction> apiRestrictions);

    /**
     * @param barcode scanned barcode
     */
    Food getFoodByBarcode(int barcode);

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
