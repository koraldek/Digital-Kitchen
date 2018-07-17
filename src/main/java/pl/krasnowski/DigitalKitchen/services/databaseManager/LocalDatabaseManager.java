package pl.krasnowski.DigitalKitchen.services.databaseManager;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.Food;

@Repository
interface LocalDatabaseManager {
    String LOCAL_DB_NAME = "Local";


    static Food getFoodByID(long foodID) {
        throw new UnsupportedOperationException();
    }
}