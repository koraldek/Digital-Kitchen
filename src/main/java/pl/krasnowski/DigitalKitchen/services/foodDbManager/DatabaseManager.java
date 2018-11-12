package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface DatabaseManager {

    String DEFAULT_FOOD_PHOTO_URL = "/img/default_food_image.png";

    String LOCAL_DB_NAME = "Local";
    String NUTRITIONIX_DB_NAME = "Nutritionix";
    String EDEMAM_DB_NAME = "Edemam";

    /**
     * Fields externalized from Nx manager.
     */
    String unauthorizedExceptionMessage = "unauthorized";
    String emptyQueryExceptionMessage = " is not allowed to be empty";
    String wrongIDorEmptyResultException = "resource not found";
    String notMatchingResultsException = "We couldn't match any of your foods";
    String NX_USER_KEY_ID = "x-remote-user-id";

    void setStrategyProfile(StrategyProfile strategyProfile);


    ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap);

    ArrayList<Food> getFoodList(String keyword);


    ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap);

    ArrayList<FoodProxy> getAutocompleteFoodList(String keyword);

    /**
     * @param barcode scanned barcode
     */
    Food getFoodByBarcode(int barcode);


    Food getFoodByID(String FoodID, String dbName);


    List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap);


    Recipe getRecipeByBarcode(int barcode);


    Recipe getRecipeByID(long recipeID, String dbName);

    /**
     * @param keyword  part of recipe name
     * @param paramMap Map of additional parameters: max kcal, diet labels
     */
    List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap);
}
