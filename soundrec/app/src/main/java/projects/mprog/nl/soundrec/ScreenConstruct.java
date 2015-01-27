package projects.mprog.nl.soundrec;

import android.content.res.Resources;

/**
 * Created by yasmina on 27-1-2015.
 */
public class ScreenConstruct {
    // Calculates dp to px.
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    // Calculates px to dp.
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
