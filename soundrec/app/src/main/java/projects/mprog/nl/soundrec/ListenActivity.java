package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogRecord;


public class ListenActivity extends Activity implements View.OnClickListener {
    String path = Environment.getExternalStorageDirectory() + "/SoundPinner/";
    String fileName;
    File file;
    MediaPlayer mediaPlayer;
    ImageButton playPauseButton;
    ImageButton stopButton;
    TextView fileNameTextView;
    EditText fileNameEditText;
    ImageView imageEdit;
    boolean isPlaying = false;
    boolean isPaused = false;
    String[] invalidCharacters = {};
    boolean isEditting = false;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        fileName = getIntent().getExtras().getString("fileName");
        file = new File(path + fileName);
        setViewFileName();
        setFileNameEditText();
        setAllOnClickListeners();
    }

    public void setViewFileName() {
        fileNameTextView = (TextView) findViewById(R.id.fileNameTextView);
        fileNameTextView.setText(fileName);
    }

    public void setAllOnClickListeners() {
        playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(this);
        stopButton = (ImageButton) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
        imageEdit = (ImageView) findViewById(R.id.imageEdit);
        imageEdit.setOnClickListener(this);
        fileNameTextView.setOnClickListener(this);
    }

    public void setFileNameEditText() {
        fileNameEditText = (EditText) findViewById(R.id.fileNameEditText);
        String s = (String) fileNameTextView.getText();
        s = s.replace(".3gpp", "");
//        fileNameEditText.setText(s);
        fileNameEditText.setVisibility(View.INVISIBLE);
    }

    public void setSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playPauseButton:
                if (!isPlaying || isPaused) try {
                    Log.d("TEST", "play");
                    playRecording();
                } catch (IOException e) {
                    Log.d("TEST", "Exception on click: " + e.getMessage());
                }
                else pausePlayback();
                Log.d("TEST", "pause");
                break;
            case R.id.stopButton:
                Log.d("TEST", "stop");
                stopPlayback();
                break;
            case R.id.fileNameTextView:
                // rename via FileConstruct
                break;
            case R.id.imageEdit:
                // some
                if (!isEditting) {
                    enableEdit();
                } else {
                    disableEdit();
                }
                break;
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
        s = s.replace(".3gpp","");
        fileNameEditText.setText("");
        fileNameEditText.append(s);
        fileNameEditText.requestFocus();
        imm.showSoftInput(fileNameEditText, 0);
    }

    private void playRecording() throws IOException {
        if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
            Log.d("TEST", "PLAY AFTER PAUSED");
        } else {
            setMediaPlayer();
            setSeekBar();
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
            Log.d("TEST", "STOP PLAYBACK");
        }
    }

}
