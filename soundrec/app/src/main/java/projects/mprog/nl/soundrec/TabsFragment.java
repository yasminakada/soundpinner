package projects.mprog.nl.soundrec;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabsFragment extends Fragment implements View.OnClickListener {
    Button recButton;
    Button browseButton;
    FragmentManager manager;

    public void setManager(FragmentManager manager){
       this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recButton = (Button) getActivity().findViewById(R.id.recordScreenButton);
        recButton.setOnClickListener(this);
        recButton.setBackgroundColor(0xFF9A9494);
        browseButton = (Button) getActivity().findViewById(R.id.browseScreenButton);
        browseButton.setOnClickListener(this);
        browseButton.setBackgroundColor(0xFFC1B9B9);

    }

    @Override
    public void onClick(View v) {
        Log.d("TEST", " ONCLICK");
        int clickedItem = v.getId();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment frag = manager.findFragmentById(R.id.fragment_container_top);
        Log.d("TEST", "FRAG: " + frag);
        if (clickedItem == R.id.recordScreenButton){
            if (!(frag instanceof RecordFragment)) {
                RecordFragment rf = new RecordFragment();
                manager.beginTransaction().replace(R.id.fragment_container_top,rf ).commit();
                recButton.setBackgroundColor(0xFF9A9494);
                browseButton.setBackgroundColor(0xFFC1B9B9);
            }
        }else if (clickedItem == R.id.browseScreenButton){
            if (!(frag instanceof BrowseFragment)) {
                BrowseFragment bf = new BrowseFragment();
                manager.beginTransaction().replace(R.id.fragment_container_top,bf ).commit();
                browseButton.setBackgroundColor(0xFF9A9494);
                recButton.setBackgroundColor(0xFFC1B9B9);
            }
        }
        Log.d("TEST", " ONCLICK");
//            if (clickedItem == R.id.recordScreenButton) manager.beginTransaction().replace(R.id.fragment_container_top,bf ).commit();
//            else if (clickedItem == R.id.browseScreenButton) ;


    }
    public void clearTabs(){
        recButton.setBackgroundColor(0xFFC1B9B9);
        browseButton.setBackgroundColor(0xFFC1B9B9);

    }
}
