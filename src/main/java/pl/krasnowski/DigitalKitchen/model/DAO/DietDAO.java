package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;

@Repository
public interface DietDAO extends JpaRepository<Diet, Long> {


    @Query(value = "SELECT user_id FROM user_diets WHERE diet_ID = :dietID",
            nativeQuery = true)
    Long getOwnerId(@Param("dietID") long dietID);
}
