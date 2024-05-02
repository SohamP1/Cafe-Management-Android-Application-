package customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import com.rucafe.cs_213_project_v.R;
import data.MenuItem;

/**
 * Custom adapter class for displaying current order items in a ListView.
 * This adapter manages a list of {@link MenuItem} objects, presenting them in the specified layout.
 *
 * @author Soham Patel
 * @version 1.0
 */
public class CustomAdapterCurrentOrder extends BaseAdapter {
    /**
     * List of MenuItem objects to be displayed
     */
    private final List<MenuItem> items;

    /**
     * LayoutInflater to inflate views
     */
    private final LayoutInflater inflater;

    /**
     * Constructs a new CustomAdapterCurrentOrder.
     *
     * @param context The current context.
     * @param items The list of MenuItems that the adapter will manage.
     */
    public CustomAdapterCurrentOrder(Context context, List<MenuItem> items) {
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the number of items in the data set represented by this Adapter.
     *
     * @return The size of the items list.
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * Gets the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return The MenuItem at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * Gets the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     * This method inflates the custom layout for each item in the ListView using the 'current_order_item_list.xml' layout resource.
     *
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.current_order_item_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.currentOrderItemList);
        MenuItem item = items.get(position);
        textView.setText(item.toString());

        return convertView;
    }
}
