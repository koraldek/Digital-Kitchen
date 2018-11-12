package pl.krasnowski.DigitalKitchen.services.foodDbManager.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;

/**
 * Access to recipes data is available only by DatabaseManager
 */
@Repository
interface RecipeDAO extends JpaRepository<Recipe, Long> {
}
