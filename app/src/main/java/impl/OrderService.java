package impl;

import data.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton service class for managing orders and current order items throughout the application.
 * This class provides centralized management of order processing, including adding items to orders,
 * finalizing orders, and accessing current and past orders.
 * @author Sasanka Paththmeistreege
 * @version 1.0
 */
public class OrderService {

    /**
     * Singleton instance of the OrderService, ensuring that only one instance of the service is created and used throughout the application.
     * The volatile keyword ensures that changes to the instance are immediately visible to all threads.
     */
    private static volatile OrderService instance;

    /**
     * List holding all finalized orders within the application. This list is used to track and retrieve past orders.
     */
    private final List<Order> allOrders = new ArrayList<>();

    /**
     * List of MenuItem objects that represent the current order being built by a user. This list is manipulated as items are added or removed from the current order.
     */
    private final List<MenuItem> currentOrderItems = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private OrderService() {}

    /**
     * Returns the singleton instance of the OrderService, creating it if it does not exist.
     * Uses double-checked locking to ensure that only one instance is created.
     *
     * @return The single instance of OrderService.
     */
    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a MenuItem to the current order if the quantity of the item is greater than 0.
     *
     * @param item The MenuItem to add to the current order.
     */
    public void addItemToCurrentOrder(MenuItem item) {
        if(item.getQuantity() > 0) {
            currentOrderItems.add(item);
        }
    }

    /**
     * Retrieves a list of all finalized orders.
     *
     * @return A list of all finalized orders.
     */
    public List<Order> getAllOrders() {
        return allOrders;
    }

    /**
     * Retrieves the list of MenuItem objects in the current order.
     *
     * @return A list of items in the current order.
     */
    public List<MenuItem> getCurrentOrderItems() {
        return currentOrderItems;
    }

    /**
     * Finalizes the current order, creating a new Order object, adding it to the list of all orders,
     * and clearing the list of current order items.
     */
    public void finalizeCurrentOrder() {
        if (!currentOrderItems.isEmpty()) {
            Order newOrder = new Order();
            newOrder.setItems(new ArrayList<>(currentOrderItems));
            allOrders.add(newOrder);
            currentOrderItems.clear();
        }
    }

    /**
     * Cancels an order by order number by removing it from the list of all orders.
     *
     * @param orderNumber The unique identifier of the order to cancel.
     */
    public void cancelOrder(String orderNumber) {
        allOrders.removeIf(order -> order.getOrderNumber().equals(orderNumber));
    }

    /**
     * Removes an item from the current order at the specified index.
     *
     * @param index The index of the item to remove.
     */
    public void removeItemAt(int index) {
        if (index >= 0 && index < currentOrderItems.size()) {
            currentOrderItems.remove(index);
        }
    }
}
