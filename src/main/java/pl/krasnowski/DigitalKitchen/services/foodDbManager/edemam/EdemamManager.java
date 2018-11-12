package pl.krasnowski.DigitalKitchen.services.foodDbManager.edemam;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.FoodDispatcher;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.RecipesDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EdemamManager implements RecipesDispatcher, FoodDispatcher {

    @Override
    public Recipe getRecipeByID(long recipeID) {
        return null;
    }


    @Override
    public List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap) {
        return null;
    }

    @Override
    public List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap) {
        return null;
    }

    @Override
    public Recipe getRecipeByBarcode(int barcode) {
        return null;
    }

    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) {
        return null;
    }

    @Override
    public ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap) {
        return null;
    }

    @Override
    public Food getFoodByID(String foodID) {
        return null;
    }

    @Override
    public Food getFoodByBarcode(int barcode) {
        return null;
    }

    @Override
    public String getDbName() {
        return DatabaseManager.EDEMAM_DB_NAME;
    }


}