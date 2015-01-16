package projects.mprog.nl.soundrec;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */
public class MainActivity2 extends FragmentActivity{
    int scrWidth = 0;
    int scrHeight = 0;

    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Resources res = getResources();

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

//        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Record",getResources().getDrawable(R.drawable.record_tab_selector)),
//                RecordFragment.class, null);
        mTabHost.addTab(mTabHost
                .newTabSpec("RecordTab")
                .setIndicator("RECORD",
                        res.getDrawable(R.drawable.record_tab_selector)),RecordFragment.class,null);
        mTabHost.addTab(mTabHost
                .newTabSpec("BrowseTab")
                .setIndicator("BROWSE",
                        res.getDrawable(R.drawable.browse_tab_selector)),BrowseFragment.class,null);

//        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Browse"),
//                BrowseFragment.class, null);
    }
}
