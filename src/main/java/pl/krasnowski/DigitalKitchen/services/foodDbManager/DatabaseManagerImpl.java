package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionService.*;


/**
 * Entry point for gathering diet/recipe (external/internal data)
 */
@Repository
@Slf4j
public class DatabaseManagerImpl implements DatabaseManager {

    @Autowired
    private StrategyProfile strategyProfile;

    @Override
    public void setStrategyProfile(StrategyProfile strategyProfile) {
        this.strategyProfile = strategyProfile;
    }

    /****************************************
     FOOD SECTION
     */
    @Override
    public Food getFoodByID(String foodID, String dbName) {
        log.debug("Running database manager with parameters: \n foodID:{}\n dbName:{}", foodID, dbName);
        return strategyProfile
                .getDbFoodManager(dbName, new int[]{ALL_QUERY_TYPES, FOOD_QUERY_TYPE})
                .getFoodByID(foodID);
    }

    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running database manager with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);
        return strategyProfile
                .getFoodDispatcher(new int[]{ALL_QUERY_TYPES, FOOD_QUERY_TYPE, AUTOCOMPLETE_QUERY_TYPE})
                .getAutocompleteFoodList(keyword, paramMap);
    }

    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword) {
        log.debug("Running database manager with parameters: \n keyword:{}}", keyword);
        return getAutocompleteFoodList(keyword, null);
    }


    @Override
    public ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running database manager with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);
        return strategyProfile
                .getFoodDispatcher(new int[]{ALL_QUERY_TYPES, FOOD_QUERY_TYPE})
                .getFoodList(keyword, paramMap);
    }

    @Override
    public ArrayList<Food> getFoodList(String keyword) {
        log.debug("Running database manager with parameters: \n keyword:{}", keyword);
        return getFoodList(keyword, null);
    }

    @Override
    public Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }


    /****************************************
     RECIPE SECTION
     */
    @Override
    public List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap) {
        log.debug("Running database manager with parameters: \n keyword:{}, paramMap:{}", keyword, paramMap);
        return strategyProfile
                .getRecipesDispatcher(new int[]{ALL_QUERY_TYPES, RECIPE_QUERY_TYPE, AUTOCOMPLETE_QUERY_TYPE})
                .getAutocompleteRecipeList(keyword, paramMap);
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