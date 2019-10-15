package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.shahcement.nirmaneyaami.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import utills.Constants;
import widget.AppAlertDialog;
import widget.AppAlertDialogListerner;
import widget.ProgressHUD;

public class LokkhonioActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] faq_pdf;
    private String[] lokh_pdf;
    private String[] video_id;

    @BindView(R.id.pdf_viewer_id_lokh)
    PDFView pdfView;

    @BindView(R.id.tab_btn_lokkhoniyo_id)
    ImageView tab_btn_lokkhoniyo_id;

    @BindView(R.id.tab_btn_question_id)
    ImageView tab_btn_question_id;

    @BindView(R.id.tab_btn_video_id)
    ImageView tab_btn_video_id;

    private String type;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lokkhonio);
        ButterKnife.bind(this);
        lokh_pdf = getIntent().getStringArrayExtra(Constants.LOKH_ARRAY);
        faq_pdf = getIntent().getStringArrayExtra(Constants.FAQ_ARRAY);
        video_id = getIntent().getStringArrayExtra(Constants.VIDEO_ARRAY);
        type = getIntent().getStringExtra(Constants.TYPE);
        position = getIntent().getIntExtra(Constants.POSITION, -1);
        if (type.equalsIgnoreCase(Constants.FAQ)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pdfView.fromAsset(faq_pdf[position]).onLoad(new OnLoadCompleteListener() {
                        public void loadComplete(int nbPages) {
                            if (mProgressHud.isShowing()) {
                                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                                pdfView.setVisibility(View.VISIBLE);


                                mProgressHud.dismiss();
                                if (!lokh_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_lokkhoniyo_id.setAlpha((float) 1);
                                }

                                if (!faq_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_question_id.setAlpha((float) 1);
                                }
                                if (!video_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_video_id.setAlpha((float) 1);
                                }

                            }
                        }
                    }).load();
                }
            }, Constants.PROGRESS_DELAY_SHORT);
        } else if (type.equalsIgnoreCase(Constants.LOKH)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pdfView.fromAsset(lokh_pdf[position]).onLoad(new OnLoadCompleteListener() {
                        public void loadComplete(int nbPages) {
                            if (mProgressHud.isShowing()) {
                                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                                pdfView.setVisibility(View.VISIBLE);
                                mProgressHud.dismiss();
                                if (!lokh_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_lokkhoniyo_id.setAlpha((float) 1);
                                }

                                if (!faq_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_question_id.setAlpha((float) 1);
                                }
                                if (!video_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_video_id.setAlpha((float) 1);
                                }
                            }
                        }
                    }).load();
                }
            }, Constants.PROGRESS_DELAY_SHORT);
        }

        tab_btn_video_id.setOnClickListener(this);
        tab_btn_lokkhoniyo_id.setOnClickListener(this);
        tab_btn_question_id.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tab_btn_lokkhoniyo_id:
                if (lokh_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                    Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    lokkhonioClick(v);
                    break;
                }
            case R.id.tab_btn_question_id:
                if (faq_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                    Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    questionsClick(v);
                    break;
                }
            case R.id.tab_btn_video_id:
                if (video_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                    Toast.makeText(getApplicationContext(), Constants.FAQ_LOKH_TEXT, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    videoClick(v);
                }
                break;
            default:
                break;
        }
    }

    private void questionsClick(View v) {
        type = Constants.FAQ;
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pdfView.fromAsset(faq_pdf[position]).onLoad(new OnLoadCompleteListener() {
                    public void loadComplete(int nbPages) {
                        if (mProgressHud.isShowing()) {
                            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                            pdfView.setVisibility(View.VISIBLE);
                            mProgressHud.dismiss();
                        }
                    }
                }).load();
            }
        }, Constants.PROGRESS_DELAY_SHORT);
    }

    private void lokkhonioClick(View v) {
        type = Constants.LOKH;
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pdfView.fromAsset(lokh_pdf[position]).onLoad(new OnLoadCompleteListener() {
                    public void loadComplete(int nbPages) {
                        if (mProgressHud.isShowing()) {
                            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                            pdfView.setVisibility(View.VISIBLE);
                            mProgressHud.dismiss();
                        }
                    }
                }).load();
            }
        }, Constants.PROGRESS_DELAY_SHORT);
    }

    private void videoClick(View v) {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.showDialog(Constants.title, Constants.message, Constants.positiveButton, Constants.negitiveButton);
        dialog.setAppAlertDialogListerner(new AppAlertDialogListerner() {
            @Override
            public void didPressPositiveButton() {
                Intent intent = new Intent(LokkhonioActivity.this, VideoPlayerActivity.class);
                intent.putExtra(Constants.POSITION, position);
                intent.putExtra(Constants.ACTIVITY_STRING, 1001);
                startActivity(intent);
            }
        });
    }

}
