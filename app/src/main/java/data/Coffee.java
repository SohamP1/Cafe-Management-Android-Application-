package data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.List;

import enums.CoffeeAddIn;
import enums.CoffeeSize;

/**
 * Represents a coffee order, including size, add-ins, and quantity.
 * Allows calculation of the total price based on these attributes.
 *
 * @author Soham Patel
 */
public class Coffee extends MenuItem {
    /**
     * The size of the coffee.
     */
    private final CoffeeSize size;

    /**
     * List of add-ins for the coffee.
     */
    private final List<CoffeeAddIn> addIns;

    /**
     * Quantity of the coffee ordered.
     */
    private final int quantity;

    /**
     * Constructs a Coffee object with specified size, add-ins, and quantity.
     *
     * @param size     The size of the coffee.
     * @param addIns   A list of add-ins for the coffee.
     * @param quantity The quantity of coffee ordered.
     */
    public Coffee(CoffeeSize size, List<CoffeeAddIn> addIns, int quantity) {
        this.size = size;
        this.addIns = addIns;
        this.quantity = quantity;
    }
    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of the coffee order, including add-ins.
     *
     * @return The total price of the coffee.
     */
    @Override
    public double price() {
        double price = size.getBasePrice() * quantity;
        if (addIns != null) {
            for (CoffeeAddIn addIn : addIns) {
                price += addIn.getPrice() * quantity;
            }
        }
        return price;
    }

    /**
     * Generates a string representation of the coffee order, including size, add-ins, quantity, and total price.
     *
     * @return A string detailing the coffee order.
     */
    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Coffee: ");
        builder.append(size.getSize());
        if (addIns != null && !addIns.isEmpty()) {
            builder.append(" + ");
            addIns.forEach(addIn -> builder.append(addIn.getCoffeeAddIn()).append(", "));
            builder.setLength(builder.length() - 2);
        }
        builder.append(" - Quantity: ").append(quantity);
        builder.append(String.format(" (Price: $%.2f)", price()));
        return builder.toString();
    }
}