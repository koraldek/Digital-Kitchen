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

/**
 * Entry point for gathering food/recipe (external/internal data)
 */
@Repository
public class DatabaseManagerImpl implements DatabaseManager, Serializable {

    private static final long serialVersionUID = 6922067532281982529L;


    @Override
    public Food getFoodByID(String FoodID, String dbName, Map<String, String> paramMap) {
        return FoodDispatcher.getFoodByID(FoodID, dbName, paramMap);
    }

    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap, Set<ApiRestriction> apiRestrictions) {
        return FoodDispatcher.getAutocompleteFoodList(keyword, paramMap, apiRestrictions);
    }


    @Override
    public ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap, Set<ApiRestriction> apiRestrictions) {
        return FoodDispatcher.getFoodList(keyword, paramMap, apiRestrictions);
    }

    @Override
    public Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }


    /*
    RECIPE SECTION
     */
    @Override
    public List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Recipe getRecipeByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Recipe getRecipeByID(long recipeID, String dbName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap) {
        throw new UnsupportedOperationException();
    }

}