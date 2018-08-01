package pl.krasnowski.DigitalKitchen.services.databaseManager;

import pl.krasnowski.DigitalKitchen.model.domains.Recipe;

import java.util.List;
import java.util.Map;


interface RecipesDispatcher {

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

    /**
     * @param keyword
     * @param paramMap
     */
    List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap);

    /**
     * @param barcode
     */
    Recipe getRecipeByBarcode(int barcode);

}