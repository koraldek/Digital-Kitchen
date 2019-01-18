package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Long>, CustomizedUserDAO {

    User findByEmailIgnoreCase(String email);

    User findByUsername(String username);


//    @Query("SELECT u FROM User u where " +
//            "u.username = :nickOrEmail OR u.email = :nickOrEmail " +
//            "AND u.password = :password")
//    User authenticateUser(@Param("nickOrEmail") String nickOrEmail, @Param("password") String password);


    /**
     * username and visibleName should be the same
     */
    List<User> findByUsernameOrVisibleNameContainingIgnoreCase(String nick, String nameVisible);

//    @Modifying
//    @Query("UPDATE User u SET u.user_id = :address WHERE c.id = :companyId")
//    Integer updateUser(long user_id, int current_silhouette, int active, int age, Timestamp birth_date, int body_type, int desired_silhouette, String email, int height, String language, int nutritionix_id, String password, int sex, String username, String visible_name, String weight, long kitchen_kitchen_id, long physical_activity_physical_activity_id, long user_id)
}