package pl.krasnowski.DigitalKitchen.services.user;

import pl.krasnowski.DigitalKitchen.model.domains.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}