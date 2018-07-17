package pl.krasnowski.DigitalKitchen.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.Role;


@Repository
public interface RoleDAO extends JpaRepository<Role, Integer>{
    Role findByroleName(String roleName);

}