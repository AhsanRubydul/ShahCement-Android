package activity;
import adapt.ImageButtonAdapter;
import adapt.MenuAdapter;
import model.MenuModel;
import android.arabin.shahcement.com.shahcement.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.util.ArrayList;
import utills.Constants;
import widget.AppAlertDialog;
import widget.AppAlertDialogListerner;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    boolean doubleBackToExitPressedOnce = false;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.grid_btn_list)
    GridView gridView;

    private ArrayList<MenuModel> menuList;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.nav_drawer_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setAppBar();
        setMenu();
        gridView.setAdapter(new ImageButtonAdapter(this,
                getResources().obtainTypedArray(R.array.btn_images)));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, GridItemDetailsActivity.class);
                intent.putExtra(Constants.POSITION, position);
                startActivity(intent);
            }
        });
    }

    private void setMenu() {

        this.menuList = new ArrayList();
        this.menuList.add(new MenuModel("ভিডিও"));
        this.menuList.add(new MenuModel("যোগাযোগ"));
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.setAdapter(new MenuAdapter(this, menuList));

    }

    private void setAppBar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);

        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    public void onMenuItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                AppAlertDialog dialog = new AppAlertDialog(this);
                dialog.showDialog(Constants.title, Constants.alertMessage, Constants.positiveButton, Constants.negitiveButton);
                dialog.setAppAlertDialogListerner(new AppAlertDialogListerner() {
                    @Override
                    public void didPressPositiveButton() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, VideoListActivity.class));
                    }
                });
                break;
//            case 1:
//                intent = new Intent(this, DrawerPdfActivity.class);
//                intent.putExtra("PDF","drawer_faq.pdf");
//                startActivity(intent);
//                break;
//            case 2:
//                intent = new Intent(this, DrawerPdfActivity.class);
//                intent.putExtra("PDF","drawer_lokh.pdf");
//                startActivity(intent);
//                break;
            case 1:
                intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"shahcement@abulkhairgroup.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Subject text here...");
                intent.putExtra("android.intent.extra.TEXT", "Body of the content here...");
                intent.putExtra("android.intent.extra.CC", "mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "বন্ধ করার জন্য পুনরায় ব্যাক চাপুন", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}

