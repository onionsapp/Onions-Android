package com.bennyguitar.onions_android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.bennyguitar.onions_android.Fragments.HomeFragment;
import com.bennyguitar.onions_android.Session.OCSession;

import edu.ua.caps.commonlyusedutils.commonlyusedutilslib.CommonlyUsedUtils;


public class MainActivity extends FragmentActivity {
    public CommonlyUsedUtils commonlyUsedUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonlyUsedUtils = new CommonlyUsedUtils(this);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HomeFragment())
                    .commit();
        }

        // Initialize OCSession
        OCSession.setUp(this);
    }

    @Override
    public void onStop(){
        // KILL THE SESSION
        OCSession.mainSession.logout();
        super.onStop();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Animate Fragment Change
    public void animateToFragment(android.support.v4.app.Fragment fragment, String tag) {
        if (fragment != null) {
            commonlyUsedUtils.switchFragmentsWithStyle(R.id.container, fragment, tag);
        }
    }

    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }
}
