package com.rucafe.android_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rucafe.cs_213_project_v.R;

import java.util.ArrayList;
import java.util.List;

import customAdapter.CustomAdapterAllOrders;
import impl.Order;
import impl.OrderService;

/**
 * Activity class to display and manage all orders.
 * Allows user to view all placed orders, select specific orders, and cancel them.
 *
 * @author Soham Patel
 * @version 1.0
 */
public class AllOrders extends AppCompatActivity {

    /**
     * ListView to display all placed orders in the system.
     * This view lists all orders for easy viewing and interaction.
     */
    private ListView allOrderListView;

    /**
     * Spinner that allows the user to select an order number from a list of available orders.
     * This selection determines which order's details are displayed and available for further actions.
     */
    private Spinner orderNumberSpinner;

    /**
     * TextView that displays the total amount of the currently selected order.
     * This field updates dynamically as different orders are selected from the orderNumberSpinner.
     */
    private TextView totalAmountTextNumber;

    /**
     * Button that enables the user to cancel the currently selected order.
     * Clicking this button will cancel the order and possibly trigger additional UI updates or confirmation dialogs.
     */
    private Button cancelOrderButton;


    /**
     * Initializes the activity with all UI components and listeners.
     * Sets up the toolbar, loads all orders, and initializes the spinner and listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        Toolbar toolbar = findViewById(R.id.allOrderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allOrderListView = findViewById(R.id.allOrderListView);
        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        totalAmountTextNumber = findViewById(R.id.totalAmountTextNumber);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        List<Order> allOrders = OrderService.getInstance().getAllOrders();
        List<String> orderNumbers = new ArrayList<>();
        for (Order order : allOrders) {
            orderNumbers.add(order.getOrderNumber());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, orderNumbers);
        orderNumberSpinner.setAdapter(spinnerAdapter);

        setupListeners();
    }

    /**
     * Sets up listeners for spinner and button components.
     * Spinner listener updates the displayed order details upon selection.
     * Button listener cancels the selected order.
     */
    private void setupListeners() {
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateListView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cancelOrderButton.setOnClickListener(v -> cancelOrder());
    }

    /**
     * Updates the ListView based on the selected order in the spinner.
     * Shows the items of the selected order and the total amount.
     *
     * @param position the position of the selected order in the spinner
     */
    @SuppressLint("DefaultLocale")
    private void updateListView(int position) {
        List<Order> allOrders = OrderService.getInstance().getAllOrders();
        Order selectedOrder = allOrders.get(position);
        CustomAdapterAllOrders adapter = new CustomAdapterAllOrders(this, selectedOrder.getItems());
        allOrderListView.setAdapter(adapter);
        totalAmountTextNumber.setText(String.format("$ %.2f", selectedOrder.calculateTotalAmount()));
    }

    /**
     * Cancels the order currently selected in the spinner.
     * Shows a toast message confirming the cancellation and closes the activity if an order is selected.
     * If no order is available or selected, it shows a toast message indicating the issue.
     */
    private void cancelOrder() {
        if (OrderService.getInstance().getAllOrders().isEmpty()) {
            Toast.makeText(this, "No order available to cancel.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPosition = orderNumberSpinner.getSelectedItemPosition();
        // Check if a valid order is selected
        if (selectedPosition < 0 || selectedPosition >= OrderService.getInstance().getAllOrders().size()) {
            Toast.makeText(this, "No order selected to cancel.", Toast.LENGTH_SHORT).show();
            return;
        }

        Order selectedOrder = OrderService.getInstance().getAllOrders().get(selectedPosition);
        if (selectedOrder != null) {
            OrderService.getInstance().cancelOrder(selectedOrder.getOrderNumber());
            Toast.makeText(this, "Order canceled successfully.", Toast.LENGTH_SHORT).show();
            finish();  // Optionally close the activity
        } else {
            Toast.makeText(this, "Failed to cancel the order.", Toast.LENGTH_SHORT).show();
        }
    }

}
