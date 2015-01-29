package projects.mprog.nl.soundrec;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */
public class MainActivity extends FragmentActivity{
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost
                .newTabSpec("RecordTab")
                .setIndicator("RECORD",
                        res.getDrawable(R.drawable.record_tab_selector)),RecordFragment.class,null);
        mTabHost.addTab(mTabHost
                .newTabSpec("BrowseTab")
                .setIndicator("BROWSE",
                        res.getDrawable(R.drawable.browse_tab_selector)),BrowseFragment.class,null);

    }
}
