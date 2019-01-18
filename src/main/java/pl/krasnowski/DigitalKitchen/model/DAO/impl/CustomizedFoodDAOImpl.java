package pl.krasnowski.DigitalKitchen.model.DAO.impl;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.DAO.CustomizedFoodDAO;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomizedFoodDAOImpl implements CustomizedFoodDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Food findByForeignID(String dbName, String foodID) {

        TypedQuery<Food> query = em.createQuery(
                "SELECT f FROM Food f"
                        + " WHERE KEY(f.foreignIDs) = :dbName AND " +
                        "VALUE(f.foreignIDs) = :foodID", Food.class);
        query.setParameter("dbName", dbName);
        query.setParameter("foodID", foodID);

        List<Food> rList = query.getResultList();

        if (rList.size() != 0)
            return rList.get(0);
        else
            return null;
    }
}