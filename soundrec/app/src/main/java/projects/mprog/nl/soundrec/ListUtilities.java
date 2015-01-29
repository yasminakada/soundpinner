package projects.mprog.nl.soundrec;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by yasmina on 28-1-2015.
 */
public class ListUtilities {
    public static File[] getAllFilesStored(){
        String path = Environment.getExternalStorageDirectory()+"/SoundPinner";

        File f = new File(path);
        File file[] = f.listFiles();
        return file;
    }
    public static String[] getAllFilenames(File[] file){
        String[] filenameArray = new String[file.length];
        for (int i=0; i < file.length; i++)
        {
            filenameArray[i] = file[i].getName();
        }
        return filenameArray;
    }

    public static String[] getAllDurations(File[] f) throws IOException {
        String[] fileDurationsArray = new String[f.length];
        for (int i=0; i < f.length; i++)
        {
            fileDurationsArray[i] = getDurationText(getDuration(f[i]));
        }
        return fileDurationsArray;
    }
    public static int getDuration(File f) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(f.getAbsolutePath());
        mediaPlayer.prepare();

        int duration = mediaPlayer.getDuration();
        mediaPlayer.release();
        return duration;
    }

    public static String getDurationText(int duration){
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int totalSeconds = 0;
        String secondsStr;
        String minutesStr;
        String hoursStr;

        totalSeconds = duration / 1000;
        seconds = totalSeconds % 60;
        minutes = (totalSeconds /60) % 60;
        hours = totalSeconds / (60 * 60);
        secondsStr = ""+seconds;
        minutesStr = ""+minutes;
        hoursStr = ""+hours;

        if (seconds < 10) secondsStr = "0"+seconds;
        if (minutes < 10) minutesStr = "0"+ minutes;
        if (hours < 10) hoursStr = "0"+ hours;
        String durationString =  hoursStr + ":" + minutesStr + ":" + secondsStr;
        Log.d("TEST", "DURATION FORMATTED: " + durationString);
        return durationString;
    }
}
