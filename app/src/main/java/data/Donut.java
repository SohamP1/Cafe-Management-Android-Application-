package data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import enums.DonutType;

/**
 * Represents a donut order, detailing the type, flavor, quantity, and allows calculation of the total price.
 *
 * @author Soham Patel
 */
public class Donut extends MenuItem {
    /**
     * The type of the donut, which determines the base price.
     */
    private final DonutType type;

    /**
     * The quantity of donuts ordered.
     */
    private final int quantity;

    /**
     * The flavor of the donut.
     */
    private final String flavor;

    /**
     * Constructs a Donut object with specified type, quantity, and flavor.
     *
     * @param type The type of the donut.
     * @param quantity The number of donuts of this type and flavor being ordered.
     * @param flavor The flavor of the donut.
     */
    public Donut(DonutType type, int quantity, String flavor) {
        this.type = type;
        this.quantity = quantity;
        this.flavor = flavor;
    }

    public DonutType getType() {
        return type;
    }

    public String getFlavor() {
        return flavor;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of the donut order based on its type and quantity.
     *
     * @return The total price for the specified number of donuts of this type.
     */
    @Override
    public double price() {
        return type.getPrice() * quantity;
    }

    /**
     * Generates a string representation of the donut order, including type, flavor, quantity, and total price.
     *
     * @return A string detailing the donut order specifics.
     */
    @NonNull
    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("Donut: %s - %s - Quantity: %d (Price: $%.2f)", type.getDonutType(), flavor, quantity, price());
    }


}
