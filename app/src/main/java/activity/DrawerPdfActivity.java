package activity;

import android.arabin.shahcement.com.shahcement.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import utills.Constants;
import widget.ProgressHUD;

public class DrawerPdfActivity extends AppCompatActivity {

    @BindView(R.id.pdf_viewer_id)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdf_drawer);
        ButterKnife.bind( this);
        final String name = getIntent().getStringExtra("PDF");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pdfView.fromAsset(name).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        if (mProgressHud.isShowing()) {
                            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                            pdfView.setVisibility(View.VISIBLE);
                            mProgressHud.dismiss();
                        }
                    }
                }).load();
            }
        },Constants.PROGRESS_DELAY);
    }

}
