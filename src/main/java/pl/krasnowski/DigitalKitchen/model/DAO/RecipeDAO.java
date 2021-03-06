package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {
}
