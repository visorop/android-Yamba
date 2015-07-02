package com.marakana.yamba.yamba;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;

public class RefreshService extends IntentService {
    private static final String TAG = "RefreshService";

    public RefreshService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent running in thread:" + Thread.currentThread());
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this); //
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");

        // Check that username and password are not empty
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

            Toast.makeText(this,
                    "Please update your username and password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "onStarted");

        DbHelper dbHelper = new DbHelper(this); //
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //
        YambaClient cloud = new YambaClient(username, password); //
        ContentValues values = new ContentValues();

        try {
            int count = 0;
            List<YambaClient.Status> timeline = cloud.getTimeline(20); //

            for (YambaClient.Status status : timeline) {

                values.clear(); //
                values.put(StatusContract.Column.ID, status.getId());
                values.put(StatusContract.Column.USER,
                        status.getUser());
                values.put(StatusContract.Column.MESSAGE,
                        status.getMessage());
                values.put(StatusContract.Column.CREATED_AT, status
                        .getCreatedAt().getTime());

                Uri uri = getContentResolver().insert(
                        StatusContract.CONTENT_URI, values); //
                if (uri != null) {
                    count++; //
                    Log.d(TAG,
                            String.format("%s: %s", status.getUser(),
                                    status.getMessage()));

                    //db.insertWithOnConflict(StatusContract.TABLE, null, values,
                    //        SQLiteDatabase.CONFLICT_IGNORE);
                    Log.d(TAG, "Statuses loaded from cloud in database.");
                }

            }
        } catch (YambaClientException e) { //
            Log.e(TAG, "Failed to fetch the timeline", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created running in thread:" + Thread.currentThread());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed running in thread:" + Thread.currentThread());
        super.onDestroy();
    }
}
