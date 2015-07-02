package com.marakana.yamba.yamba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                this.startActivity(new Intent(this, SettingsActivity.class));
                break;
            }
            case R.id.action_tweet: {
                this.startActivity(new Intent(this, StatusActivity.class));
                break;
            }
            case R.id.action_refresh: {
                startService(new Intent(this, RefreshService.class));
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}
