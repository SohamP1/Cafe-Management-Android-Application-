package customAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rucafe.cs_213_project_v.R;

/**
 * Custom ArrayAdapter for displaying a list of items, each item having text and an associated image.
 * Used typically for a main navigation menu where each entry is represented by a label and an icon.
 * @author Soham Patel
 * @version 1.0
 */
public class CustomAdapterMainView extends ArrayAdapter<String> {
    /**
     * Context in which the adapter is running
     */
    private final Context context;

    /**
     * Text values for each item
     */
    private final String[] values;

    /**
     * Drawable resources for each item
     */
    private final int[] images;

    /**
     * Constructor for the CustomAdapterMainView.
     *
     * @param context The current context.
     * @param values Array of strings representing the text for each item.
     * @param images Array of drawable resource IDs for the images corresponding to each item.
     */
    public CustomAdapterMainView(Context context, String[] values, int[] images) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.images = images;
    }

    /**
     * Gets a view that displays the data at the specified position in the data set.
     * Each item has a TextView to display text and an ImageView to display an icon.
     *
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView textView = rowView.findViewById(R.id.mainViewListText);
        ImageView imageView = rowView.findViewById(R.id.mainViewImage);

        textView.setText(values[position]);
        imageView.setImageResource(images[position]);

        return rowView;
    }
}
