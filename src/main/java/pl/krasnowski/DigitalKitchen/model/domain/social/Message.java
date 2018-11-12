package pl.krasnowski.DigitalKitchen.model.domain.social;


import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "messages")
@Data
@Component
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long message_ID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User sender;

    String content = "";

    boolean isRead = false;

    @Enumerated(EnumType.ORDINAL)
    private Folder folder;

    private Timestamp readDate;

    @Override
    public String toString() {
        return "Message{" +
                "message_ID=" + message_ID +
            //    ", sender=" + sender.getUsername() + //Default toString() had cyclical reference to User object.
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", folder=" + folder +
                '}';
    }
}
