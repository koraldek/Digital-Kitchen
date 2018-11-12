package pl.krasnowski.DigitalKitchen.model.domain.diet;

/**
 * Contains percentages of nutrients in diet.
 */
public enum DietType {
    moderate(0.5, 0.25, 0.25),
    balanced(0.40, 0.30, 0.30),
    optimal(0.15, 0.20, 0.65),
    richProtein(0.40, 0.30, 0.30),
    lowFat(0.6, 0.25, 0.15),
    lowProtein(0.6, 0.1, 0.3),
    lowCarbohydrate(0.1, 0.45, 0.45),
    ketogenic(0.5, 0.35, 0.6),
    custom(0, 0, 0);

    private final double carbohydrates, proteins, fat;

    DietType(double carbohydrates, double proteins, double fat) {
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFat() {
        return fat;
    }
}