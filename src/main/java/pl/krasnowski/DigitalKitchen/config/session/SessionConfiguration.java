package pl.krasnowski.DigitalKitchen.config.session;


import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

//@Component
public class SessionConfiguration extends AbstractHttpSessionApplicationInitializer {

    //TODO: change to @Value{} from application.properties
    public final static int SESSION_TIMEOUT = 120 * 60;

}