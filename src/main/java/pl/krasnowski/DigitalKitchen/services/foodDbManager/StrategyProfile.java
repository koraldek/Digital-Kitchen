package pl.krasnowski.DigitalKitchen.services.foodDbManager;


import org.springframework.stereotype.Service;

/**
 * Choose database which fits best with current user API restrictions and chooses strategy profile
 */
@Service
public interface StrategyProfile {

    FoodDispatcher getDbFoodManager(String dbName, int[] queryTypes);

    RecipesDispatcher getDbRecipeManager(String dbName, int[] queryTypes);

    FoodDispatcher getFoodDispatcher(int[] queryTypes);

    RecipesDispatcher getRecipesDispatcher(int[] queryTypes);
}
