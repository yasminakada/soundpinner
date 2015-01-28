package projects.mprog.nl.soundrec;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */
public class BrowseFragment extends Fragment implements AdapterView.OnItemClickListener {
    Button audioFile;
    ListView list;
    String[] itemNames;
    String[] itemDuration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        File[] f = getAllFilesStored();
        itemNames = getAllFileNames(f);

        list = (ListView) getActivity().findViewById(R.id.listView);
        try {
            list.setAdapter(new BrowseListAdapter(getActivity().getBaseContext()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.setOnItemClickListener(this);
    }

    public File[] getAllFilesStored(){
        String path = Environment.getExternalStorageDirectory()+"/SoundPinner";
        Log.d("FILES", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("FILES", "Size: "+ file.length);
        return file;
    }
    public String[] getAllFileNames(File[] file){
        String[] fileNames = new String[file.length];
        for (int i=0; i < file.length; i++)
        {
            fileNames[i] = file[i].getName();
        }
        return fileNames;
    }
    public int getDuration(File f) throws IOException {
        int duration = 0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(f.getAbsolutePath());
        mediaPlayer.prepare();
        Log.d("TEST", "DURATION in miliseconds"+ mediaPlayer.getDuration());
        duration = mediaPlayer.getDuration();
        mediaPlayer.release();
        return duration;
    }

    public String getDurationText(int duration){
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

        if (seconds < 9) secondsStr = "0"+seconds;
        if (minutes < 9) minutesStr = "0"+ minutes;
        if (hours < 9) hoursStr = "0"+ hours;
        String durationString =  hoursStr + ":" + minutesStr + ":" + secondsStr;
        Log.d("TEST", "DURATION FORMATTED: " + durationString);
        return durationString;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView tv = (TextView) view.findViewById(R.id.fileName);
        String fileName = (String) tv.getText();

        Intent i = new Intent(getActivity(),ListenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fileName",fileName);
        i.putExtras(bundle);
        TabHost host = (TabHost) getActivity().findViewById(android.R.id.tabhost);
        host.setCurrentTab(0);
        startActivity(i);

        // popup some fragment with playing possibilities and name changing possibilities.

    }
}

