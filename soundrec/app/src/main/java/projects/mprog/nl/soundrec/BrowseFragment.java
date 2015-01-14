package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */
public class BrowseFragment extends Fragment implements View.OnClickListener{
    Button audioFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        audioFile = (Button) getActivity().findViewById(R.id.audioFileExample);
        audioFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (v.getId() == R.id.audioFileExample){
            ListenFragment lf = new ListenFragment();
            manager.beginTransaction().replace(R.id.fragment_container_top,lf).commit();
            TabsFragment tf = (TabsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_bottom);
            tf.clearTabs();
        }
    }
}
