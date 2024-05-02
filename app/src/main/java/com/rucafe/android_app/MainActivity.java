package com.rucafe.android_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.rucafe.cs_213_project_v.R;

import customAdapter.CustomAdapterMainView;

/**
 * Main activity class that serves as the entry point for the application.
 * Displays options to navigate to various order activities and access current orders.
 *
 * @author Sasanka Paththameistreege
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * This method is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById to programmatically interact with widgets in the UI,
     * and setting up any initial adapters, listeners, intents.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ListView to display main navigation options
        ListView listView = findViewById(R.id.mainListView);

        // Sample data for ListView options
        String[] listViewList = {"Order Donuts", "Order Coffee", "Order Sandwiches", "All Orders"};
        int[] listViewImages = {R.drawable.order_donuts, R.drawable.order_coffee, R.drawable.order_sandwiche, R.drawable.all_order};

        // Setting up a custom adapter to bind data to the ListView
        CustomAdapterMainView adapter = new CustomAdapterMainView(this, listViewList, listViewImages);
        listView.setAdapter(adapter);

        // Setup item click listener to navigate to different activities based on selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent( MainActivity.this,OrderDonutsActivity.class));
                    break;
                case 1:
                    startActivity(new Intent( MainActivity.this,OrderCoffeeActivity.class));
                    break;
                case 2:
                    startActivity(new Intent( MainActivity.this,OrderSandwichesActivity.class));
                    break;
                case 3:
                    startActivity(new Intent( MainActivity.this,AllOrders.class));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }
        });

        // Initialize the ImageButton for current orders
        ImageButton currentOrderButton = findViewById(R.id.currentOrderButton);

        // Set an OnClickListener to navigate to the CurrentOrder activity when clicked
        currentOrderButton.setOnClickListener(v -> {
            // Start CurrentOrderActivity
            startActivity(new Intent(MainActivity.this, CurrentOrder.class));
        });
    }
}