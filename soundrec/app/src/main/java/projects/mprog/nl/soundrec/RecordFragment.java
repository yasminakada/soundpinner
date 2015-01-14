package projects.mprog.nl.soundrec;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment implements View.OnClickListener {
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;
    private MediaController mediaContoller;
    private String OUTPUT_FILE;

    Button recButton;
    Button playButton;


    //TODO: Using media controller?
    //TODO: Storing every file and keeping track of name and place.
    //TODO: Time counter.
    //TODO: All strings for setting a buttons text should be a constant/variable.
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recButton = (Button) getActivity().findViewById(R.id.recordButton);
        recButton.setOnClickListener(this);
        playButton = (Button) getActivity().findViewById(R.id.playBackButton);
        playButton.setOnClickListener(this);


        // TODO: have the user enter a name for the file
        // and store this in a array? and on device.
        Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gpp";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recordButton:
                try {
                    if (recButton.getText().equals("RECORD"))
                        beginRecording();
                    else if (recButton.getText().equals("STOP"))
                        stopRecording();
                } catch (Exception e) {
                    Log.d("TEST", "--Exception beginRecording caught. :" + e.getMessage());
                }
                break;
            case R.id.playBackButton:
                try {
                    if (playButton.getText().equals("LISTEN"))
                        playRecording();
                    else if (playButton.getText().equals("STOP"))
                        stopPlayback();
                } catch (Exception e) {
                    Log.d("TEST", "--Exception playRecording caught. :" + e.getMessage());
                }
                break;
//            case R.id.stopFileButton:
//                try {
//                    stopPlayback();
//                } catch (Exception e) {
//                    Log.d("TEST", "--Exception stopPlayback caught. :" + e.getMessage());
//                }
//                break;
        }
    }

    private void beginRecording() throws IOException {
        clearMediaRecorder();
        File outFile = new File(OUTPUT_FILE);

        if (outFile.exists())
            outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // TODO: setAudioEncoder to NB when low api and WB when high api.
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();
        recButton.setText("STOP");
    }

    private void clearMediaRecorder() {
        // TODO: remove option of recording while already recording!
        if (recorder != null)
            recorder.release();
    }

    private void stopRecording() {
        // TODO: Give user option to save file with chosen filename
        if (recorder != null)
            recorder.stop();
        recButton.setText("RECORD");
    }

    private void playRecording() throws IOException {
        clearMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
        playButton.setText("STOP");
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

    private void stopPlayback() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}
