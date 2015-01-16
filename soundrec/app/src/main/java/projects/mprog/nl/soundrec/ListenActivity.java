package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class ListenActivity extends Activity implements View.OnClickListener {
    String path = Environment.getExternalStorageDirectory() + "/SoundPinner/";
    String fileName;
    File file;
    MediaPlayer mediaPlayer;
    ImageButton playPauseButton;
    ImageButton stopButton;
    Boolean isPlaying = false;
    Boolean isPaused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        fileName = getIntent().getExtras().getString("fileName");
        file = new File(path + fileName);
        setViewFileName();
        setButtons();

    }

    private void playRecording() throws IOException {
        if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
            Log.d("TEST","PLAY AFTER PAUSED");
        } else {
            clearMediaPlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.pause_48);
            isPlaying = true;
            Log.d("TEST","PLAY FROM START");

        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                Log.d("TEST","PLAYBACK FINISHED");
                playPauseButton.setImageResource(R.drawable.play_48);
                isPlaying = false;
            }
        });

    }

    private void clearMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                Log.d("TEST", "--Exception clear mediaplayer caught. :" + e.getMessage());
            }
        }
    }

    private void pausePlayback() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
                isPaused = true;
                playPauseButton.setImageResource(R.drawable.play_48);
            }
        }

    }

    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            isPaused = false;
            playPauseButton.setImageResource(R.drawable.play_48);
            Log.d("TEST","STOP PLAYBACK");
        }
    }

    public void setButtons(){
        playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(this);
        stopButton = (ImageButton) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
    }


    public void setViewFileName() {
        TextView tv = (TextView) findViewById(R.id.filePlaying);
        tv.setText(fileName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playPauseButton:
                if(!isPlaying || isPaused) try {
                    Log.d("TEST","play");
                    playRecording();
                } catch (IOException e) {
                    Log.d("TEST","Exception on click: " + e.getMessage());
                }
                else pausePlayback();
                Log.d("TEST","pause");
                break;
            case R.id.stopButton:
                Log.d("TEST","stop");
                stopPlayback();
                break;
        }

    }
}
