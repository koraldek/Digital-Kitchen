package pl.krasnowski.DigitalKitchen.model.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;

import java.util.List;

@Repository
public interface ApiRestrictionDAO extends JpaRepository<ApiRestriction, Long> {
    List<ApiRestriction> findByDbName(String dbName);
}
