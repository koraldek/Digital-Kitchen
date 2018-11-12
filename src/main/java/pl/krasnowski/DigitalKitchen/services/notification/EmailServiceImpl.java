package pl.krasnowski.DigitalKitchen.services.notification;

import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    /*
        TODO: Obtain api-key to use SendGrid client.
     */
//    private final SendGrid sendGrid;

    @Override
    public void sendEmail(Mail mail) {
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            //     this.sendGrid.api(request);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendNotification(User recipient, String content) {
        log.debug("Sending notification to {}, through email service: {}", recipient.getUsername(), content);
        //TODO:Add sendEmail method
    }
}
