package ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.shahcement.nirmaneyaami.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import utiity.Constants;

public class YouTubePlayerActivity extends PreBaseActivity {

    private static final String TAG = YouTubePlayerActivity.class.getSimpleName();

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_player);
        ButterKnife.bind(this);
        String[] video_id = getResources().getStringArray(R.array.video_id_array);
        int position = getIntent().getIntExtra(Constants.POSITION, -1);


        if (getIntent().getIntExtra(Constants.ACTIVITY_STRING, -1) == Constants.VEDIO_LIST_ACTIVITY) {
            if (position < 19)
                initializeYouTubePlayer(video_id[position]);
            else
                initializeYouTubePlayer(video_id[position + 1]);
        } else {
            initializeYouTubePlayer(video_id[position]);
        }
    }

    private void initializeYouTubePlayer(final String video_id) {
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
                video_id +
                "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(video, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }


    public void onBackPressed() {

        finish();
    }

}
