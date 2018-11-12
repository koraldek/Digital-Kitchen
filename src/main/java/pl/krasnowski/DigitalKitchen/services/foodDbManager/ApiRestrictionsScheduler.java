package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.DAO.ApiRestrictionDAO;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;

import java.util.List;

@Component
public class ApiRestrictionsScheduler {
    private final static long daySchedule = 86400000L;

    @Autowired
    ApiRestrictionDAO apiRestrictionDAO;

    @Autowired
    ApiRestrictionService apiRestrictionService;


    @Scheduled(initialDelay = daySchedule, fixedDelay = daySchedule)
    public void resetNxRestrictions() {
        List<ApiRestriction> ar = apiRestrictionDAO.findByDbName(DatabaseManager.NUTRITIONIX_DB_NAME);
        ar.forEach(apiRestriction -> apiRestrictionService.resetDbRestrictions(apiRestriction));
    }


    public void resetEdemamresctritions() {
        throw new UnsupportedOperationException();
    }
}
