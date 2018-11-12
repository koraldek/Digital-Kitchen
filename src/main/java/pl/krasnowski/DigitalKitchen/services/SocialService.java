package pl.krasnowski.DigitalKitchen.services;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.social.Message;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.notification.NotificationService;

import java.util.List;

@Service
public interface SocialService extends NotificationService {

    Message createWelcomeMessage(String content);

    Message createMessage(String content, User sender);

    List<Message> getUserMessages();

    void sendMessage(String content, User sender, User recipient);

    void deleteMessage(Message message, User owner);

    void addFriend(User newFriend);

    List<User> getFriends();

    void removeFriend(User friendToDelete);
}
