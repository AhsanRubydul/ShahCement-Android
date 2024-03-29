package utiity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer player;
    public static String audioFileName = "";
    private static MyMediaPlayer myMediaPlayer = new MyMediaPlayer();
    private Context appContext;

    private MyMediaPlayer() {
    }

    public static Context getMyMediaPlayer() {
        return getInstance().getContext();
    }

    public static synchronized MyMediaPlayer getInstance() {
        return myMediaPlayer;
    }

    public void init(Context context) {
        if (appContext == null) {
            this.appContext = context;
        }
    }

    private Context getContext() {
        return appContext;
    }

    public static void stopPlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public static boolean isPlaying() {
        if (player != null) {
            return player.isPlaying();
        }
        return false;
    }

    public static void playAudio(String fileName) {
        try {
            if (player != null) {
                player.stop();
                player.release();
                player = null;
            }

            player = new MediaPlayer();

            AssetFileDescriptor descriptor = getMyMediaPlayer().getAssets().openFd(fileName);
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            player.prepare();
            player.setVolume(1f, 1f);
            player.setLooping(false);
            player.start();

            audioFileName = fileName;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
