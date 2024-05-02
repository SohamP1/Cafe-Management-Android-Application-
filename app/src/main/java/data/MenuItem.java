package data;

/**
 * Abstract class representing a menu item in a cafe or restaurant ordering system.
 * This class serves as a base for all specific menu items (like Coffee, Donut, Sandwich, etc.)
 * and defines a common interface for calculating the price of an item.
 * Subclasses are required to implement the {@code price()} method to calculate
 * the specific item's price based on its attributes.
 *
 * @author Soham Patel
 */
public abstract class MenuItem {

    /**
     * Calculates the price of the menu item.
     * Implementation of this method is required in subclasses to determine
     * the specific calculation logic based on the item's attributes.
     *
     * @return the price of the menu item as a double.
     */
    public abstract double price();

    public abstract int getQuantity();

}
