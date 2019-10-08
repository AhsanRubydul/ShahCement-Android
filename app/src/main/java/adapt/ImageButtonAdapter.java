package adapt;

import android.app.Activity;
import android.arabin.shahcement.com.shahcement.R;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ImageButtonAdapter extends BaseAdapter {

    private Activity context;
    private TypedArray images;

    public ImageButtonAdapter(Activity context, TypedArray images) {
        this.context = context;
        this.images = images;
    }

    public int getCount() {
        return images.length();
    }

    public Object getItem(int position) {
        return images.getDrawable(position);
    }

    public long getItemId(int position) {
        return images.getResourceId(position, 0);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        ImageView imageView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_list_item, null);
            imageView = convertView.findViewById(R.id.grid_list_image_id);
            imageView.getLayoutParams().width = width / 2;
            imageView.getLayoutParams().height = width / 2;
            imageView.requestLayout();
            imageView.setPadding(15, 15, 15, 15);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageDrawable(images.getDrawable(position));
            return convertView;
        }
        imageView = convertView.findViewById(R.id.grid_list_image_id);
        imageView.getLayoutParams().width = width / 2;
        imageView.getLayoutParams().height = width / 2;
        imageView.requestLayout();
        imageView.setPadding(15, 15, 15, 15);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(images.getDrawable(position));
        return convertView;
    }
}