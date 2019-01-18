package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;


import java.util.List;

/*
Bridge between Common, Branded and system stored food
 */
interface IFood {

    String getName();

    String getPhoto();

    int getServingWeightGrams();

    AltMeasure getPrimaryServingMeasure();

    List<AltMeasure> getAltMeasures();

    List<FullNutrient> getFullNutrients();

    String getFoodID();
}


