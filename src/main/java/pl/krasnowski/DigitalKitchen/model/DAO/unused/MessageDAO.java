package pl.krasnowski.DigitalKitchen.model.DAO.unused;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.social.Message;

@Repository
public interface MessageDAO extends JpaRepository<Message, Long> {
}
