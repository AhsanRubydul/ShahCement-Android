package model;

public class YoutubeVideoModel {

        private String videoDuration;
        private String videoId;
        private String videoTitle;

        public YoutubeVideoModel(String videoId, String videoTitle, String videoDuration) {
            this.videoId = videoId;
            this.videoTitle = videoTitle;
            this.videoDuration = videoDuration;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getVideoDuration() {
            return videoDuration;
        }

        public void setVideoDuration(String videoDuration) {
            this.videoDuration = videoDuration;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("YoutubeVideoModel{videoId='");
            stringBuilder.append(videoId);
            stringBuilder.append('\'');
            stringBuilder.append(", videoTitle='");
            stringBuilder.append(this.videoTitle);
            stringBuilder.append('\'');
            stringBuilder.append(", videoDuration='");
            stringBuilder.append(videoDuration);
            stringBuilder.append('\'');
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

}
