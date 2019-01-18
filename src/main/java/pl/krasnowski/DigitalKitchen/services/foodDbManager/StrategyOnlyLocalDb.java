package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.local.LocalDatabaseManager;

@Service
@Slf4j
@AllArgsConstructor
public class StrategyOnlyLocalDb implements StrategyProfile {

    @Autowired
    private final LocalDatabaseManager localDatabaseManager;

    @Override
    public FoodDispatcher getDbFoodManager(String dbName, int[] queryType) {
        return localDatabaseManager;
    }


    @Override
    public RecipesDispatcher getDbRecipeManager(String dbName, int[] queryType) {
        return localDatabaseManager;
    }

    @Override
    public FoodDispatcher getFoodDispatcher(int[] queryType) {
        return localDatabaseManager;
    }

    @Override
    public RecipesDispatcher getRecipesDispatcher(int[] queryType) {
        return localDatabaseManager;
    }
}