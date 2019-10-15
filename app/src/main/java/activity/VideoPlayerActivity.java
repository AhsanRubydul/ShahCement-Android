package activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.shahcement.nirmaneyaami.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import utills.Constants;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    private static final String TAG = VideoPlayerActivity.class.getSimpleName();
    private int activity_code;
    private int position_act;
    private String type;

    @BindView(R.id.you_tube_player_id)
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        String[] video_id = getResources().getStringArray(R.array.video_id_array);
        int position = getIntent().getIntExtra(Constants.POSITION, -1);
        type = getIntent().getStringExtra(Constants.TYPE);
        activity_code = getIntent().getIntExtra(Constants.ACTIVITY_STRING, -1);
        position_act = position;
        initializeYouTubePlayer(video_id[position]);
    }

    private void initializeYouTubePlayer(final String video_id) {

        youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer
                    youTubePlayer, boolean wasRestored) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        //onBackPressed();
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
                youTubePlayer.loadVideo(video_id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(VideoPlayerActivity.TAG, "Youtube Player View initialization failed");
            }
        });
    }


    public void onBackPressed() {
       /* if (this.activity_code == 1001) {
            startActivity(new Intent(this, GridItemDetailsActivity.class));
            finish();
        } else if (this.activity_code == Constants.VEDIO_LIST_ACTIVITY) {
            finish();
        } else if (this.activity_code == Constants.LOKKHONIO_CODE) {
            Intent intent = new Intent(this, LokkhonioActivity.class);
            intent.putExtra(Constants.POSITION, this.position_act);
            intent.putExtra(Constants.TYPE, this.type);
            startActivity(intent);
            finish();
        }*/

        finish();
    }

}
