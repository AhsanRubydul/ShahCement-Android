package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.shahcement.nirmaneyaami.R;

import java.util.ArrayList;

import adapt.YoutubeVideoAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.YoutubeVideoModel;
import utills.Constants;
import utills.RecyclerViewOnClickListener;

public class VideoListActivity extends AppCompatActivity {

    @BindView(R.id.video_thumbs_list_id)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vedio_list);
        ButterKnife.bind(this);
        populateRecyclerList();
    }

    private void populateRecyclerList() {
        final ArrayList<YoutubeVideoModel> videoList = generateVideoList();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new YoutubeVideoAdapter(this, videoList));

        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(VideoListActivity.this, VideoPlayerActivity.class);
                intent.putExtra(Constants.POSITION, position);
                intent.putExtra(Constants.ACTIVITY_STRING, Constants.VEDIO_LIST_ACTIVITY);
                startActivity(intent);
            }
        }));
    }

    private ArrayList<YoutubeVideoModel> generateVideoList() {
        ArrayList<YoutubeVideoModel> youTubeList = new ArrayList();
        String[] videoId = getResources().getStringArray(R.array.video_id_array);
        String[] videoTitle = getResources().getStringArray(R.array.video_title_array);
        String[] videoDuration = getResources().getStringArray(R.array.video_duration_array);
        for (int i = 0; i < videoId.length; i++) {
            if (!videoId[i].equalsIgnoreCase("N/A")) {
                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel("id", "title", "duration");
                youtubeVideoModel.setVideoId(videoId[i]);
                youtubeVideoModel.setVideoTitle(videoTitle[i]);
                youtubeVideoModel.setVideoDuration(videoDuration[i]);
                youTubeList.add(youtubeVideoModel);
            }
        }
        return youTubeList;
    }

    public void onBackPressed() {
        finish();
    }

}
