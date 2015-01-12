package projects.mprog.nl.soundrec;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements Communicator{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void respond(String data) {
        FragmentManager manager = getFragmentManager();
        manager.findFragmentById(R.id.fragmentRecord);




            if (data == "browsescreen") {
                try {
                    Fragment fragmentMain = manager.findFragmentById(R.id.fragmentRecord);
                    FragmentTransaction ft = manager.beginTransaction();
                } catch (Exception e) {
                    Log.d("TEST", "FRAGMENT IS DIFFERENT FROM RECORD");
                }
            }
    }
}
