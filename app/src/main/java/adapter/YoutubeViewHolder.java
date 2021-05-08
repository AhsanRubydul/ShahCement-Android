package adapter;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.shahcement.nirmaneyaamii.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class YoutubeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_duration_label_id)
    TextView durationLabelId;

    @BindView(R.id.video_title_label_id)
    TextView thumbTitle;

    @BindView(R.id.video_thumbnail_image_view_id)
    YouTubeThumbnailView thumbnailView;

    public YoutubeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
