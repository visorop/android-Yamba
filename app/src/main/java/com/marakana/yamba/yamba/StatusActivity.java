package com.marakana.yamba.yamba;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class StatusActivity extends ActionBarActivity {

    private static Context APP_CONTEXT;

    private static final String TAG = "StatusActivity";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "password";

    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;
    private int maxCount;


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

    private class StatusActivityTextCountTextWatcher implements TextWatcher{

        //private int maxCount = Integer.parseInt(textCount.getText().toString());

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d(TAG,"TextWatcher:afterTextChanged entered.");

            int currentCount = maxCount - editStatus.length();
            textCount.setText(Integer.toString(currentCount));

            if(currentCount < (int)(0.2 * maxCount)){
                textCount.setTextColor(Color.RED);
            }
            else if(currentCount < maxCount){
                textCount.setTextColor(Color.GREEN);
            }
            else{
                textCount.setTextColor(defaultTextColor);
            }
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
        this.textCount = (TextView) this.findViewById(R.id.textCount);
        this.defaultTextColor = textCount.getTextColors().getDefaultColor();

        Resources res = this.getResources();
        this.maxCount = res.getInteger(R.integer.max_text_count);

        this.buttonTweet.setOnClickListener(statusActivityOnClickListener);
        this.editStatus.addTextChangedListener(new StatusActivityTextCountTextWatcher());
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
