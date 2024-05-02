package impl;

import androidx.annotation.NonNull;

import data.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order in the system, maintaining a list of menu items, and capable of calculating subtotal,
 * sales tax, and total amount. Each order is uniquely identifiable by an order number.
 *
 * @author Sasanka Paththmeistreege
 * @version 1.0
 */
public class Order {
    /**
     * Unique identifier for each order, generated to ensure each order can be distinctly identified.
     */
    private final String orderNumber;

    /**
     * List of menu items included in this order.
     */
    private List<MenuItem> items;

    /**
     * Static counter used to generate unique order numbers incrementally for each new order.
     */
    private static int orderNumberCounter = 1;

    /**
     * Constant representing the sales tax rate applied to the subtotal of the order.
     */
    public static final double salesTax = 0.06625;


    /**
     * Constructs a new Order with a unique order number and initializes the list of items.
     */
    public Order() {
        this.orderNumber = generateUniqueOrderNumber();
        this.items = new ArrayList<>();
    }

    /**
     * Sets the items in the order.
     *
     * @param items List of MenuItem objects to be added to the order.
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Returns the unique order number of this order.
     *
     * @return A string representing the order number.
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the list of items in the order.
     *
     * @return A list of MenuItem objects.
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Calculates the subtotal for the order by summing the prices of all items.
     *
     * @return The subtotal value.
     */
    public double calculateSubtotal() {
        return items.stream().mapToDouble(MenuItem::price).sum();
    }

    /**
     * Calculates the sales tax for the order based on the subtotal and the static tax rate.
     *
     * @return The sales tax amount.
     */
    public double calculateSalesTax() {
        return calculateSubtotal() * salesTax;
    }

    /**
     * Calculates the total amount due for the order by adding the subtotal and sales tax.
     *
     * @return The total amount due.
     */
    public double calculateTotalAmount() {
        return calculateSubtotal() + calculateSalesTax();
    }

    /**
     * Generates a unique order number using a static counter.
     *
     * @return A unique order number as a String.
     */
    private static String generateUniqueOrderNumber() {
        return "Order#" + (orderNumberCounter++);
    }

    /**
     * Provides a string representation of the order including the order number and list of items.
     *
     * @return A string detailing the order number and items.
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Number: " + orderNumber + "\nItems:\n");
        for (MenuItem item : items) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}
