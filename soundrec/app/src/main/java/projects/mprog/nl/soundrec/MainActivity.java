package projects.mprog.nl.soundrec;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */
public class MainActivity extends FragmentActivity{
    int scrWidth = 0;
    int scrHeight = 0;
    TabsFragment tabsFragment;
    RecordFragment recordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabsFragment = new TabsFragment();
        recordFragment = new RecordFragment();

        setScreenDimensions();
        setFragmentContainersDimensions();
        setFragmentsView();
        FileConstruct.createMainDirectory();
    }
    public void setScreenDimensions() {
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

    public void setFragmentsView(){
        FragmentManager manager = getSupportFragmentManager();
        tabsFragment.setManager(manager);
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragment_container_top, recordFragment);
        ft.add(R.id.fragment_container_bottom, tabsFragment);

        ft.commit();
    }

    public void setFragmentContainersDimensions(){
        if (scrHeight > 0) {
            FrameLayout topContainer = (FrameLayout) findViewById(R.id.fragment_container_top);
            FrameLayout bottomContainer = (FrameLayout) findViewById(R.id.fragment_container_bottom);

            ViewGroup.LayoutParams params = topContainer.getLayoutParams();
            params.height =(scrHeight / 5) * 4;

            params = bottomContainer.getLayoutParams();
            params.height =(scrHeight / 5);

        }
    }

}
