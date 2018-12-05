package pl.krasnowski.DigitalKitchen.model.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.DAO.CustomizedUserDAO;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.domain.user.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
@Transactional
public class CustomizedUserDAOImpl implements CustomizedUserDAO {

    @Autowired
    User user;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void updateUserDTO(UserDTO userDTO) {
        User user_ref = em.find(User.class, userDTO.getUserID());
        log.debug("Updating user with data:" + userDTO.toString());
        BeanUtils.copyProperties(userDTO, user_ref);

        em.unwrap(Session.class).evict(user_ref); // get Session object to evict user object
        em.merge(user_ref);

        BeanUtils.copyProperties(user_ref,user);
    }

    @Override
    public void updateUser(User updatedUser) {
        User user_ref = em.find(User.class, updatedUser.getUserID());
        log.debug("Updating user with data:" + updatedUser.toString());
        BeanUtils.copyProperties(updatedUser, user_ref);

        em.unwrap(Session.class).evict(user_ref);
        em.merge(user_ref);
        BeanUtils.copyProperties(user_ref,user);
    }
}
