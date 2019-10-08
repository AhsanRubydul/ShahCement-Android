package adapt;

import android.app.Activity;
import activity.MainActivity;
import android.arabin.shahcement.com.shahcement.R;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import model.MenuModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<MenuModel> menuList;

    public MenuAdapter(Activity context, ArrayList<MenuModel> menuList) {
        this.context = context;
        this.menuList = menuList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.drawer_row_menu,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemName.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "font/solaimanlipinormal.ttf"));
        holder.itemName.setText((menuList.get(position)).getTitle());
        holder.itemName.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)context).onMenuItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.menu_item)
        TextView itemName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}