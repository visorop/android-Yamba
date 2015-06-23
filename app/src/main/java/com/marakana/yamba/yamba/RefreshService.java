package com.marakana.yamba.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RefreshService extends IntentService {
    private static final String TAG = "RefreshService";

    public RefreshService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created.");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed.");
        super.onDestroy();
    }
}
