package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

class AltMeasure {
    int servingWeight, seq, qty;
    String measure;

    AltMeasure(int servingWeight, String measure) {
        this.servingWeight = servingWeight;
        this.measure = measure;
    }
}
