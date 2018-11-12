package pl.krasnowski.DigitalKitchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.DAO.DietDAO;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

@Service
public class OwnerValidator {

    @Autowired
    DietDAO dietDAO;

    public boolean isDietOwner(long dietID, Authentication auth) {

        User user = (User) auth.getPrincipal();
        long ownerID = dietDAO.getOwnerId(dietID);
        return user.getUserID() == ownerID;
    }
}
