package ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.shahcement.nirmaneyaami.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import utiity.Constants;
import utiity.Helper;
import utiity.MyMediaPlayer;
import widget.AppAlertDialog;
import widget.AppAlertDialogListener;
import widget.ProgressHUD;

public class GridItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] faq_pdf;
    private String[] lokkhoniyo_pdf;
    private String[] main_pdf;
    private String[] video_id;
    private String[] audio_id;
    private int position;

    @BindView(R.id.pdf_viewer_id)
    PDFView pdfView;

    @BindView(R.id.tab_btn_lokkhoniyo_id)
    ImageView tab_btn_lokkhoniyo_id;

    @BindView(R.id.tab_btn_question_id)
    ImageView tab_btn_question_id;

    @BindView(R.id.tab_btn_video_id)
    ImageView tab_btn_video_id;

    @BindView(R.id.tab_btn_audio_id)
    ImageView tab_btn_audio_id;

    MyMediaPlayer myMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProgressHUD mProgressHud = ProgressHUD.show(this, "LOADING ...", true);
        position = getIntent().getIntExtra(Constants.POSITION, 0);
        preparePdfString();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_grid_item_details);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pdfView.fromAsset(main_pdf[position]).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        if (mProgressHud.isShowing()) {
                            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                            pdfView.setVisibility(View.VISIBLE);
                            mProgressHud.dismiss();
                        }

                        if (!lokkhoniyo_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                            tab_btn_lokkhoniyo_id.setAlpha((float) 1);
                        }

                        if (!faq_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                            tab_btn_question_id.setAlpha((float) 1);
                        }
                        if (!video_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                            tab_btn_video_id.setAlpha((float) 1);
                        }
                        if (!audio_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                            tab_btn_audio_id.setAlpha((float) 1);
                            myMediaPlayer = MyMediaPlayer.getInstance();
                            myMediaPlayer.init(getApplicationContext());
                        }
                    }
                }).load();
            }
        }, Constants.PROGRESS_DELAY);

        tab_btn_video_id.setOnClickListener(this);
        tab_btn_lokkhoniyo_id.setOnClickListener(this);
        tab_btn_question_id.setOnClickListener(this);
        tab_btn_audio_id.setOnClickListener(this);
    }


    private void preparePdfString() {
        main_pdf = getResources().getStringArray(R.array.main_pdf_array);
        lokkhoniyo_pdf = getResources().getStringArray(R.array.lokkhoniyo_pdf);
        faq_pdf = getResources().getStringArray(R.array.faq_pdf);
        video_id = getResources().getStringArray(R.array.video_id_array);
        audio_id = getResources().getStringArray(R.array.audio_file_array);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_btn_lokkhoniyo_id:
                if (lokkhoniyo_pdf[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
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

            case R.id.tab_btn_audio_id:
                audiosClick(v, position);
                break;
        }
    }


    private void questionsClick(View v) {
        Intent intent = new Intent(this, LokkhonioActivity.class);
        intent.putExtra(Constants.POSITION, this.position);
        intent.putExtra(Constants.TYPE, Constants.FAQ);
        intent.putExtra(Constants.LOKH_ARRAY, this.lokkhoniyo_pdf);
        intent.putExtra(Constants.FAQ_ARRAY, this.faq_pdf);
        intent.putExtra(Constants.VIDEO_ARRAY, this.video_id);
        startActivity(intent);
    }

    private void lokkhonioClick(View v) {
        Intent intent = new Intent(this, LokkhonioActivity.class);
        intent.putExtra(Constants.POSITION, this.position);
        intent.putExtra(Constants.TYPE, Constants.LOKH);
        intent.putExtra(Constants.LOKH_ARRAY, this.lokkhoniyo_pdf);
        intent.putExtra(Constants.FAQ_ARRAY, this.faq_pdf);
        intent.putExtra(Constants.VIDEO_ARRAY, this.video_id);
        startActivity(intent);
    }

    private void videoClick(View v) {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.showDialog(Constants.title, Constants.message, Constants.positiveButton, Constants.negativeButton);
        dialog.setAppAlertDialogListerner(new AppAlertDialogListener() {
            @Override
            public void didPressPositiveButton() {
                Intent intent = new Intent(GridItemDetailsActivity.this, YouTubePlayerActivity.class);
                intent.putExtra(Constants.POSITION, position);
                intent.putExtra(Constants.ACTIVITY_STRING, 1001);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    private void audiosClick(View v, int position) {
        MyMediaPlayer.playAudio(audio_id[position]);

    }

}
