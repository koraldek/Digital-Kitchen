package pl.krasnowski.DigitalKitchen.model.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.user.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}