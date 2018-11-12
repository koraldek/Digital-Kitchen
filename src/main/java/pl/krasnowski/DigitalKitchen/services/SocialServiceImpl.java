package pl.krasnowski.DigitalKitchen.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.social.Folder;
import pl.krasnowski.DigitalKitchen.model.domain.social.Message;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.List;

@Service
@Slf4j
public class SocialServiceImpl implements SocialService {

    @Autowired
    private final UserService uService;


    public SocialServiceImpl(@Lazy UserService uService) {
        this.uService = uService;
    }

    @Override
    public Message createWelcomeMessage(String content) {
        Message nMessage = new Message();
        nMessage.setContent(content);
        nMessage.setFolder(Folder.UNREAD);
        nMessage.setSender(uService.findByUsername("Admin"));

        log.debug("Created welcome message:{}", nMessage.getContent());
        return nMessage;
    }

    @Override
    public Message createMessage(String content, User sender) {
        Message nMessage = new Message();

        nMessage.setContent(content);
        nMessage.setFolder(Folder.UNREAD);
        nMessage.setRead(false);
        nMessage.setSender(sender);
        return nMessage;
    }

    @Override
    public List<Message> getUserMessages() {
        return uService.getCurrentUser().getMessages();
    }

    @Override
    public void sendMessage(String content, User sender, User recipient) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setSender(sender);
        msg.setFolder(Folder.UNREAD);
        msg.setRead(false);

        uService.findByUsername(recipient.getUsername()).addMessage(msg);
        log.debug("Sent message from:{}, to:{}", sender.getUsername(), recipient.getUsername());
    }

    @Override
    public void deleteMessage(Message message, User owner) {
        uService.findByUsername(owner.getUsername()).removeMessage(message);
    }


    @Override
    public void addFriend(User newFriend) {
        uService.getCurrentUser().getFriends().add(newFriend);
    }

    @Override
    public List<User> getFriends() {
        return uService.getCurrentUser().getFriends();
    }

    @Override
    public void removeFriend(User friendToDelete) {
        uService.getCurrentUser().getFriends().remove(friendToDelete);
    }

    @Override
    public void sendNotification(User recipient, String content) {
        sendMessage(content,
                uService.findByUsername("Admin"),
                recipient);
    }
}
