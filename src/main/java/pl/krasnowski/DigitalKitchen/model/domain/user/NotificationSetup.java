package pl.krasnowski.DigitalKitchen.model.domain.user;


import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.events.NotificationDestination;
import pl.krasnowski.DigitalKitchen.model.events.SystemEventType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Component
@Table(name = "notification_setup")
public class NotificationSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_setup_id")
    private long notificationSetupID;

    @Column(name = "notification_source")
    @Enumerated
    private SystemEventType notificationSource;


    @Column(name = "notification_destination")
    @Enumerated
    @ElementCollection
    private Set<NotificationDestination> notificationDestination;
}

