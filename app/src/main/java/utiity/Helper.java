package utiity;

import android.content.res.Resources;

public class Helper {

    public static int convertDpToPixel(int dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().density));
    }

    public static int convertPixelToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().density));
    }

}
