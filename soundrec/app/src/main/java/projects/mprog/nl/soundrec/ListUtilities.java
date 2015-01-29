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
        Log.d("FILES", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("FILES", "Size: "+ file.length);
        return file;
    }
    public static String[] getAllFileNames(File[] file){
        String[] fileNames = new String[file.length];
        for (int i=0; i < file.length; i++)
        {
            fileNames[i] = file[i].getName();
        }
        return fileNames;
    }

    public static String[] getAllDurations(File[] f) throws IOException {
        String[] filesDurations = new String[f.length];
        for (int i=0; i < f.length; i++)
        {
            filesDurations[i] = getDurationText(getDuration(f[i]));
        }
        return filesDurations;
    }
    public static int getDuration(File f) throws IOException {
        int duration = 0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(f.getAbsolutePath());
        mediaPlayer.prepare();
        Log.d("TEST", "DURATION in miliseconds"+ mediaPlayer.getDuration());
        duration = mediaPlayer.getDuration();
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
