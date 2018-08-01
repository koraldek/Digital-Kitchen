package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;


import java.util.List;

/*
Bridge between Common and Branded food
 */
public interface IFood {
    String getName();

    String getPhoto();

    int getServingGramsWeight();

    List<AltMeasure> getAltMeasures();

    List<FullNutrient> getFullNutrients();

    String getFoodID();
}


