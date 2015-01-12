package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabsFragment extends Fragment implements View.OnClickListener {
    Button recButton;
    Button browseButton;
    Communicator comm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        recButton = (Button) getActivity().findViewById(R.id.recordScreenButton);
        browseButton = (Button) getActivity().findViewById(R.id.browseScreenButton);
    }

    @Override
    public void onClick(View v) {
        int clickedItem = v.getId();
            if (clickedItem == R.id.recordScreenButton) comm.respond("recordscreen");
            else if (clickedItem == R.id.browseScreenButton) comm.respond("browsescreen");

    }
}
