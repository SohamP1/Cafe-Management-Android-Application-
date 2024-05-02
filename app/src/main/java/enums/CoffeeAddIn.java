package enums;

/**
 * Enumerates the add-in options for coffee.
 * Each add-in option has a descriptive name and a standard price.
 *
 * @author Soham Patel
 */
public enum CoffeeAddIn {
    /**
     * Represents an add-in of sweet cream to a coffee order.
     */
    SWEET_CREAM("Sweet Cream"),

    /**
     * Represents an add-in of French vanilla flavor to a coffee order.
     */
    FRENCH_VANILLA("French Vanilla"),

    /**
     * Represents an add-in of Irish cream flavor to a coffee order.
     */
    IRISH_CREAM("Irish Cream"),

    /**
     * Represents an add-in of caramel flavor to a coffee order.
     */
    CARAMEL("Caramel"),

    /**
     * Represents an add-in of mocha flavor to a coffee order.
     */
    MOCHA("Mocha");

    /**
     * The descriptive name of the coffee add-in.
     */
    private final String coffeeAddIn;

    /**
     * Constructs a CoffeeAddIn enum constant.
     *
     * @param coffeeAddIn The descriptive name of the coffee add-in.
     */
    CoffeeAddIn(String coffeeAddIn) {
        this.coffeeAddIn = coffeeAddIn;
    }

    /**
     * Returns the fixed price associated with any coffee add-in.
     * Currently, all add-ins have a standardized additional cost.
     *
     * @return The price of the coffee add-in.
     */
    public double getPrice() {
        return 0.30;
    }

    /**
     * Returns the name of the coffee add-in.
     *
     * @return The descriptive name of the coffee add-in.
     */
    public String getCoffeeAddIn() {
        return coffeeAddIn;
    }

}
