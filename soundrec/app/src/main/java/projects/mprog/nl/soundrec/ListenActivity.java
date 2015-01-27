package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import java.io.File;
import java.io.IOException;


public class ListenActivity extends Activity implements View.OnClickListener {

    String path = Environment.getExternalStorageDirectory() + "/SoundPinner/";
    String fileName;
    File file;

    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    ImageButton playPauseButton;
    ImageButton stopButton;
    TextView fileNameTextView;
    TextView elapsedTimeText;
    TextView durationText;
    EditText fileNameEditText;
    ImageView imageEdit;
    ImageView imageDelete;

    boolean isPlaying = false;
    boolean isPaused = false;
    boolean isEditting = false;
    private int scrWidth;
    private int scrHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        fileName = getIntent().getExtras().getString("fileName");
        file = new File(path + fileName);
        findViews();
        setAllOnClickListeners();
        setInitialInterface();

        setScreenDimensions();

        setMediaPlayer();
        setSeekBar();
        seekUpdate();

        elapsedTimeText.setText(getTimeString(0));
        durationText.setText(getTimeString(mediaPlayer.getDuration()));
    }

    private void setScreenDimensions() {
        // Getting the screen size of the device
        if (Build.VERSION.SDK_INT >= 13) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            scrWidth = size.x;
            scrHeight = size.y;
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            scrWidth = display.getWidth();
            scrHeight = display.getHeight();
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (mediaPlayer != null) {
                seekUpdate();
            }
        }
    };

    public void seekUpdate() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(runnable, 1000);
    }

    // getting views from xml
    private void findViews() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
        stopButton = (ImageButton) findViewById(R.id.stopButton);

        fileNameEditText = (EditText) findViewById(R.id.fileNameEditText);
        fileNameTextView = (TextView) findViewById(R.id.fileNameTextView);

        elapsedTimeText = (TextView) findViewById(R.id.elapsedTimeListen);
        durationText = (TextView) findViewById(R.id.durationListen);

        imageEdit = (ImageView) findViewById(R.id.imageEdit);
        imageDelete = (ImageView) findViewById(R.id.imageDelete);
    }

    // initial settings for the interface
    private void setInitialInterface() {
        fileNameTextView.setText(fileName);

        fileNameEditText.setVisibility(View.INVISIBLE);
    }

    public void setAllOnClickListeners() {
        playPauseButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        imageEdit.setOnClickListener(this);
        imageDelete.setOnClickListener(this);
        fileNameTextView.setOnClickListener(this);
    }

    public void setSeekBar() {
        int seekWidth = (scrWidth/5) * 4;

        android.view.ViewGroup.LayoutParams param = seekBar
                .getLayoutParams();
        param.width = seekWidth;
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d("TEST", "Progress elapsed: " + getTimeString(progress));
                elapsedTimeText.setText(getTimeString(progress));
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void setMediaPlayer() {
        clearMediaPlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.d("TEST", e.getMessage());
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playPauseButton:
                // not playing or paused will lead to recording being played.
                // else recording will be paused
                if (!isPlaying || isPaused) {
                    Log.d("TEST", "play");
                    playRecording();
                } else if (isPlaying) {
                    pausePlayback();
                    Log.d("TEST", "pause");
                }
                break;

            case R.id.stopButton:
                Log.d("TEST", "stop");
                stopPlayback();
                break;

            case R.id.imageEdit:
                if (!isEditting) {
                    enableEdit();
                } else {
                    disableEdit();
                }
                break;
            case R.id.imageDelete:
                deleteFile();
                break;
        }
    }

    private void playRecording() {
        if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
            Log.d("TEST", "PLAY AFTER PAUSED");
            playPauseButton.setImageResource(R.drawable.pause_48);
        } else {
            setMediaPlayer();
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.pause_48);
            isPlaying = true;
            Log.d("TEST", "PLAY FROM START");
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                Log.d("TEST", "PLAYBACK FINISHED");
                playPauseButton.setImageResource(R.drawable.play_48);
                isPlaying = false;
            }
        });

    }

    private void pausePlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            isPaused = true;
            playPauseButton.setImageResource(R.drawable.play_48);
        }
    }

    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            isPaused = false;
            playPauseButton.setImageResource(R.drawable.play_48);
            Log.d("TEST", "STOP PLAYBACK");
        }
    }

    private void disableEdit() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        isEditting = false;
        imageEdit.setImageResource(R.drawable.edit_32);
        fileNameTextView.setVisibility(View.VISIBLE);
        fileNameEditText.setVisibility(View.INVISIBLE);
        imm.hideSoftInputFromWindow(fileNameEditText.getWindowToken(), 0);
        String newName = fileNameEditText.getText().toString();
        if (!newName.equals(fileNameTextView.getText())) {
            FileConstruct.renameFile(file, newName);
            fileNameTextView.setText(newName + ".3gpp");
            file = new File(file.getParent(),newName + ".3gpp");
        }

    }

    private void enableEdit() {
        // enables renaming the file.
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        isEditting = true;
        imageEdit.setImageResource(R.drawable.check_32);
        fileNameTextView.setVisibility(View.INVISIBLE);
        fileNameEditText.setVisibility(View.VISIBLE);
        String s = (String) fileNameTextView.getText();
        s = s.replace(".3gpp", "");
        fileNameEditText.setText("");
        fileNameEditText.append(s);
        fileNameEditText.requestFocus();
        imm.showSoftInput(fileNameEditText, 0);
    }

    private void deleteFile(){new AlertDialog.Builder(this)
            .setTitle("Delete recording")
            .setMessage("Are you sure you want to delete this recording: " + fileName + " ?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    file.delete();
                    finish();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();

    }
    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


}
