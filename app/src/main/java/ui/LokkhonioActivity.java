package ui;

import android.content.Intent;
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
import utiity.MyMediaPlayer;
import widget.AppAlertDialog;
import widget.AppAlertDialogListener;
import widget.ProgressHUD;

public class LokkhonioActivity extends PreBaseActivity implements View.OnClickListener {

    private String[] faq_pdf;
    private String[] lokh_pdf;
    private String[] video_id;
    private String[] audio_id;

    @BindView(R.id.pdf_viewer_id_lokh)
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
        audio_id = getResources().getStringArray(R.array.audio_file_array);
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
                                if (!audio_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_audio_id.setAlpha((float) 1);
                                    myMediaPlayer = MyMediaPlayer.getInstance();
                                    myMediaPlayer.init(getApplicationContext());
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
                                if (!audio_id[position].equalsIgnoreCase(Constants.NOT_APPLICABLE)) {
                                    tab_btn_audio_id.setAlpha((float) 1);
                                    myMediaPlayer = MyMediaPlayer.getInstance();
                                    myMediaPlayer.init(getApplicationContext());
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
        tab_btn_audio_id.setOnClickListener(this);
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
            case R.id.tab_btn_audio_id:
                audiosClick(v, position);
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
        dialog.showDialog(Constants.title, Constants.message, Constants.positiveButton, Constants.negativeButton);
        dialog.setAppAlertDialogListerner(new AppAlertDialogListener() {
            @Override
            public void didPressPositiveButton() {
                Intent intent = new Intent(LokkhonioActivity.this, YouTubePlayerActivity.class);
                intent.putExtra(Constants.POSITION, position);
                intent.putExtra(Constants.ACTIVITY_STRING, 1001);
                startActivity(intent);
            }
        });
    }

    private int audiosClick(View v, int position) {
        boolean isPlaying = MyMediaPlayer.isPlaying();

        if (isPlaying)
            if (MyMediaPlayer.audioFileName.equalsIgnoreCase(audio_id[position])) {
                MyMediaPlayer.stopPlayer();
                return 0;
            }

        MyMediaPlayer.playAudio(audio_id[position]);
        return 1;
    }


}
