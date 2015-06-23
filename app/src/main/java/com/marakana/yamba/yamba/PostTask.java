package com.marakana.yamba.yamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class PostTask extends AsyncTask<String, Void, String>{

    private static final String TAG = "PostTask";
    private static final String ONSUCCESS = "Successfully sent message.";
    private static final String ONFAILURE = "Failure while sending message...";

    private Context fragmentContext = StatusFragment.getFragmentContext(); // get app main context through a static getter

    @Override
    protected String doInBackground(String... params) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(fragmentContext);
        String userName = prefs.getString("username","");
        String password = prefs.getString("password","");


        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            fragmentContext.startActivity(new Intent(fragmentContext,SettingsActivity.class));
            return "You need to enter username and password!";
        }


        YambaClient yambaClient = new YambaClient(userName,password);
        try {
            yambaClient.postStatus(params[0]);
            Log.d(TAG, ONSUCCESS);
            return ONSUCCESS;
        } catch (YambaClientException e) {
            Log.d(TAG, ONFAILURE + "\n" + e.getMessage() + e.getStackTrace());
            return ONFAILURE;
        }
    }
    @Override
    protected void onPostExecute(String result){

        Toast toast = Toast.makeText(fragmentContext,result,Toast.LENGTH_LONG);
        toast.show();
    }
}
