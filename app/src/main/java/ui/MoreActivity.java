package ui;

import android.os.Bundle;
import android.view.Window;

import com.shahcement.nirmaneyaami.databinding.ActivityMoreBinding;

import utiity.Constants;

public class MoreActivity extends PreBaseActivity {

    private ActivityMoreBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appToolBar.toolbar);
        getSupportActionBar().setTitle("");


        binding.ivPhone.setOnClickListener(v -> {
            openDialer(Constants.SHAH_CEMENT_HOT_LINE);
        });

        binding.ivWeb.setOnClickListener(v -> {
            showURLInApp(Constants.SHAH_CEMENT_WEB);
        });

        binding.ivEmail.setOnClickListener(v -> {
            sendEmail(Constants.SHAH_CEMENT_EMAIL);
        });

        binding.ivHome.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.ivEShop.setOnClickListener(v -> {
            showURLInApp(Constants.SHAH_CEMENT_E_SHOP);
        });

        binding.ivVideo.setOnClickListener(v -> {
            showVideoList(this);
        });

        binding.ivBlog.setOnClickListener(v -> {
            showURLInApp(Constants.SHAH_CEMENT_BLOG);
        });
    }


}

