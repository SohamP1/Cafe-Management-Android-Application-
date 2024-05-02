package enums;

/**
 * Enumerates available add-on options for sandwiches, each associated with a specific price.
 * This enumeration helps to standardize the selection and pricing of sandwich add-ons across the application,
 * ensuring a consistent offering and facilitating the calculation of the total sandwich price.
 *
 * @author Soham Patel
 */
public enum SandwichAddOn {
    /**
     * Represents the option to add cheese to a sandwich.
     */
    CHEESE("Cheese", 1.0),

    /**
     * Represents the option to add lettuce to a sandwich.
     */
    LETTUCE("Lettuce", 0.3),

    /**
     * Represents the option to add tomatoes to a sandwich.
     */
    TOMATOES("Tomatoes", 0.3),

    /**
     * Represents the option to add onions to a sandwich.
     */
    ONIONS("Onions", 0.3);

    /**
     * The descriptive name of the add-on.
     */
    private final String addOn;

    /**
     * The price associated with the add-on.
     */
    private final double price;

    /**
     * Constructs a SandwichAddOn enumeration constant with the specified add-on name and price.
     *
     * @param addOn The descriptive name of the add-on.
     * @param price The price of the add-on.
     */
    SandwichAddOn(String addOn, double price) {
        this.addOn = addOn;
        this.price = price;
    }

    /**
     * Retrieves the price of the add-on.
     *
     * @return The price associated with this add-on.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the descriptive name of the add-on.
     *
     * @return The name of the add-on.
     */
    public String getAddOn() {
        return addOn;
    }
}
