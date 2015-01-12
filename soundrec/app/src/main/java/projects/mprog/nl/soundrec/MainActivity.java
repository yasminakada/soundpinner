package projects.mprog.nl.soundrec;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabsFragment tabsFragment = new TabsFragment();
        RecordFragment recordFragment = new RecordFragment();

        FragmentManager manager = getSupportFragmentManager();
        tabsFragment.setManager(manager);
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragment_container_top, recordFragment);
        ft.add(R.id.fragment_container_bottom, tabsFragment);

        ft.commit();
    }

}
