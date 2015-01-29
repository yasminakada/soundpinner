package projects.mprog.nl.soundrec;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.MediaController;

import java.io.File;
import java.io.IOException;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */

public class RecordFragment extends Fragment implements View.OnClickListener {
    private MediaRecorder recorder;
    private String outputPath;

    boolean isRecording = false;

    File file;

    Button recButton;
    Chronometer chronometer;

    FragmentManager manager;
    RenameDialogFragment dialog;

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
        recButton.setBackgroundResource(R.drawable.mic_circle_grey_256);
        recButton.setOnClickListener(this);

        chronometer = (Chronometer) getActivity().findViewById(R.id.chronometer);

        manager = getActivity().getSupportFragmentManager();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recordButton){
            try {
                if (!isRecording)
                  startRecording();
                else
                    stopRecording();
            } catch (Exception e) {
                Log.d("TEST", "Caught exception - startRecording - " + e.getMessage());
            }
        }
    }

    private void startRecording() throws IOException {
        clearMediaRecorder();
        outputPath = FileUtilities.getOutputPath();
        file = new File(outputPath);

        isRecording = true;

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        // setAudioEncoder to NB when low api and WB when high api.
        if (android.os.Build.VERSION.SDK_INT < 10)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        else
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(outputPath);
        recorder.prepare();
        recorder.start();

        // chronometer starts counting...
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //button bg change
        recButton.setBackgroundResource(R.drawable.mic_circle_red_256);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        dialog = new RenameDialogFragment();
        dialog.setFile(file);
    }

    private void stopRecording() {
        if (recorder != null)
            recorder.stop();
        chronometer.stop();

        dialog.show(manager,"dialog");

        isRecording = false;
        recButton.setBackgroundResource(R.drawable.mic_circle_grey_256);

    }

    private void clearMediaRecorder() {
        if (recorder != null)
            recorder.release();
    }
}
