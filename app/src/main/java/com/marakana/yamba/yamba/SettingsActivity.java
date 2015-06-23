package com.marakana.yamba.yamba;

import android.app.Activity;
import android.os.Bundle;


public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (savedInstance == null) {
            SettingsFragment settingsFragment = new SettingsFragment();
            this.getFragmentManager().beginTransaction().add(android.R.id.content, settingsFragment, settingsFragment.getClass().getName()).commit();
        }
    }
}
