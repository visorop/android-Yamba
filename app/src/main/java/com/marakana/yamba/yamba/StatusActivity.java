package com.marakana.yamba.yamba;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class StatusActivity extends ActionBarActivity {

    private static Context APP_CONTEXT;

    private static final String TAG = "StatusActivity";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "password";

    private EditText editStatus;
    private Button buttonTweet;


    private class StatusActivityOnClickListener implements OnClickListener {
        @Override
        public void onClick(View clickedView) {
            switch(clickedView.getId()) {
                case R.id.buttonTweet : {
                    String status = editStatus.getText().toString(); //
                    Log.d(TAG, "onClicked with status: " + status);

                    new PostTask().execute(status, USERNAME, PASSWORD);
                }
            }
            //may add more clicks events here with additional cases
        }
    }

    private StatusActivityOnClickListener statusActivityOnClickListener = new StatusActivityOnClickListener();

    public static Context getAppContext() {
        return APP_CONTEXT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP_CONTEXT = getApplicationContext();
        setContentView(R.layout.activity_status);

        this.editStatus = (EditText) this.findViewById(R.id.editStatus);
        this.buttonTweet = (Button) this.findViewById(R.id.buttonTweet);
        this.buttonTweet.setOnClickListener(statusActivityOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
