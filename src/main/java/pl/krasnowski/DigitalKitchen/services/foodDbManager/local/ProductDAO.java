package pl.krasnowski.DigitalKitchen.services.foodDbManager.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Product;

@Repository
 interface ProductDAO extends JpaRepository<Product, Long> {
}
