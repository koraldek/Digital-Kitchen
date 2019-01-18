package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;

import java.util.List;
import java.util.Map;

@Component
public interface RecipesDispatcher {

    Recipe getRecipeByID(long recipeID);

    /**
     * @param keyword  name or part of recipe's name
     * @param paramMap Map of additional parameters: max KCAL, diet labels
     */
    List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap);

    /**
     * @param keyword  name or part of recipe's name
     * @param paramMap Map of additional parameters: max KCAL, diet labels
     */
    List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap);

    /**
     * @param barcode code from label
     */
    Recipe getRecipeByBarcode(int barcode);

    String getDbName();

}