package utiity;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;

public class Helper {

    public static int convertDpToPixel(int dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().density));
    }

    public static int convertPixelToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().density));
    }

   /* public static void playAssetSound(Context context, String soundFileName) {
        try {
             MyMediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            AssetFileDescriptor descriptor = context.getAssets().openFd(soundFileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
