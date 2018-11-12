package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domain.user.RestrictionPiece;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ApiRestrictionServiceImpl implements ApiRestrictionService {

    @Autowired
    private UserService uService;

    private List<RestrictionPiece> restrictionPiecesByQueryType(String dbName, Class dataType, int queryType) {
        List<RestrictionPiece> result = new ArrayList<>();

        for (RestrictionPiece rp : getConcreteApiRestriction(dbName))
            if ((rp.getQueryType() == queryType || rp.getQueryType() == ALL_QUERY_TYPES) && rp.getDataType() == dataType)
                result.add(rp);
        return result;
    }


    @Override
    public Set<RestrictionPiece> getConcreteApiRestriction(String dbName) {
        ApiRestriction apiRestriction = uService.getCurrentUser().getApiRestrictions().stream()
                .filter(ar -> ar.getDbName().equals(dbName))
                .findFirst()
                .orElse(null);
        if (apiRestriction == null)
            return new HashSet<>();

        return apiRestriction.getRestrictionsMap();
    }

    public static Set<ApiRestriction> buildAPIRestrictions() {
        Set<ApiRestriction> apiRestrictions = new HashSet<>();
        apiRestrictions.add(ApiRestrictionService.buildNutritionixApiRestriction());
        apiRestrictions.add(ApiRestrictionService.buildEdemamApiRestriction());
        return apiRestrictions;
    }


    @Override
    public void addRestrictionPiece(String dbName, int period, int max, Class dataType, int queryType) {
        for (RestrictionPiece rp : getConcreteApiRestriction(dbName)) {
            if (rp.getPeriod() == period && rp.getDataType() == dataType && rp.getQueryType() == queryType) {
                throw new IllegalArgumentException("Restriction piece already exist. Given input values: dbName:" + dbName
                        + ",period:" + period + ",dataType:" + dataType.toString() + ",queryType:" + queryType);
            }
        }
        getConcreteApiRestriction(dbName).add(new RestrictionPiece(period, max, dataType, queryType));
    }

    @Override
    public RestrictionPiece getRestrictionPiece(String dbName, int period, Class dataType, int queryType) {
        for (RestrictionPiece rp : getConcreteApiRestriction(dbName)) {
            System.out.println("rp:" + rp);
            if (rp.getPeriod() == period && rp.getDataType() == dataType && rp.getQueryType() == queryType) {
                return rp;
            }
        }
        throw new IllegalArgumentException("Cannot find restriction piece for given input values: dbName:" + dbName
                + ",period:" + period + ",dataType:" + dataType.toString() + ",queryType:" + queryType);
    }

    @Override
    public void removeRestrictionPiece(String dbName, RestrictionPiece rp) {
        getConcreteApiRestriction(dbName).remove(rp);
    }

    @Override
    public void increment(String dbName, Class dataType, int[] queryType) {

        for (int i = 0; i < queryType.length; i++) {
            int finalI = i;
            restrictionPiecesByQueryType(dbName, dataType, i)
                    .forEach(rp -> {
                        if (rp.getDataType().equals(dataType) && rp.getQueryType() == finalI) {
                            rp.setCurrent(rp.getCurrent() + 1);
                        }
                    });
        }
        uService.updateUser(uService.getCurrentUser());
    }


    @Override
    public void resetAll(String dbName) {
        for (RestrictionPiece rp : getConcreteApiRestriction(dbName))
            rp.setCurrent(0);
        log.debug("Successfully reset db:{}", dbName);
    }

    /**
     * Resets current values of selected dbName or every database if dbName is absent.
     */
    @Override
    public void resetDbRestrictions(ApiRestriction ar) {
        ar.resetRestrictions();
        log.debug("Successfully reset db:{}", ar.getDbName());
    }

    @Override
    public int getCurrentValue(String dbName, int period, Class dataType, int queryType) {
        return getRestrictionPiece(dbName, period, dataType, queryType).getCurrent();
    }

    @Override
    public int getMaxValue(String dbName, int period, Class dataType, int queryType) {
        return getRestrictionPiece(dbName, period, dataType, queryType).getMax();
    }

    @Override
    public boolean isRestrictionReached(String dbName, Class dataType, int[] queryType) {
        for (int qt : queryType) {
            for (RestrictionPiece rp : restrictionPiecesByQueryType(dbName, dataType, qt)) {
                if (rp.getCurrent() == rp.getMax())
                    return true;
            }
        }

        return false;
    }

    @Override
    public String getCurrentValues(String dbName, Class dataType, int queryType) {
        StringBuilder sb = new StringBuilder("Current/Max/Period[min]: " + getQueryTypeName(queryType) + ":");
        ArrayList<RestrictionPiece> rpList = (ArrayList<RestrictionPiece>) restrictionPiecesByQueryType(dbName, dataType, queryType);

        rpList.forEach(rp ->
                sb.append(rp.getCurrent()).append("/")
                        .append(rp.getMax()).append("/")
                        .append(rp.getPeriod()));
        return sb.toString();
    }


    private String getQueryTypeName(int queryType) {
        switch (queryType) {
            case ALL_QUERY_TYPES:
                return "all";
            case AUTOCOMPLETE_QUERY_TYPE:
                return "autocomplete";
            case BARCODE_QUERY_TYPE:
                return "barcode";
            case FOOD_QUERY_TYPE:
                return "food";
            case RECIPE_QUERY_TYPE:
                return "recipe";
            default:
                throw new IllegalArgumentException("Unknown queryType argument value");
        }
    }
}