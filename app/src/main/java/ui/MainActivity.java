package ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.shahcement.nirmaneyaami.R;
import com.shahcement.nirmaneyaami.databinding.ActivityMainBinding;

import adapter.GridItemAdapter;
import utiity.Constants;

public class MainActivity extends PreBaseActivity {

    boolean doubleBackToExitPressedOnce = false;
    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appToolBar.toolbar);
        getSupportActionBar().setTitle("");

        binding.gridView.setVerticalScrollBarEnabled(false);
        binding.gridView.setAdapter(new GridItemAdapter(this, getResources().obtainTypedArray(R.array.btn_images)));
        binding.gridView.setOnItemClickListener((parent, view, position, id) -> {
            showGridItemDetails(position);
        });

        binding.ivPhone.setOnClickListener(v -> {
            openDialer(Constants.SHAH_CEMENT_HOT_LINE);
        });

        binding.ivWeb.setOnClickListener(v -> {
            showURLInApp(Constants.SHAH_CEMENT_WEB);
        });

        binding.ivEmail.setOnClickListener(v -> {
            sendEmail(Constants.SHAH_CEMENT_EMAIL);
        });

        binding.ivMore.setOnClickListener(v -> {
            showMore();
        });

        binding.bottomBar.setOnClickListener(v -> {
            showMore();
        });
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

