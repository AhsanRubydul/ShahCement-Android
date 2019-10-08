package adapt;

import android.arabin.shahcement.com.shahcement.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;

class YoutubeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_duration_label_id)
    TextView durationLebelId;

    @BindView(R.id.video_title_label_id)
    TextView thumbTitle;

    @BindView(R.id.video_thumbnail_image_view_id)
    YouTubeThumbnailView thumbnailView;

    public YoutubeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
