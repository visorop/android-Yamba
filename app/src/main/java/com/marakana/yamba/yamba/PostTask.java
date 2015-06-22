package com.marakana.yamba.yamba;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class PostTask extends AsyncTask<String, Void, String>{

    private static final String TAG = "PostTask";
    private static final String ONSUCCESS = "Successfully sent message.";
    private static final String ONFAILURE = "Failure while sending message...";

    @Override
    protected String doInBackground(String... params) {
        YambaClient yambaClient = new YambaClient(params[1],params[2]);
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

        //super.onPostExecute(result);

        Context appContext = StatusActivity.getAppContext(); // get app main context through a static getter

        Toast toast = Toast.makeText(appContext,result,Toast.LENGTH_LONG);
        toast.show();
    }
}
