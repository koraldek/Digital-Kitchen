package pl.krasnowski.DigitalKitchen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findyByNickname(String nickname);
}