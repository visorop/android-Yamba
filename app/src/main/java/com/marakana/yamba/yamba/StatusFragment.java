package com.marakana.yamba.yamba;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class StatusFragment extends Fragment {

    private static Context FRAGMENT_CONTEXT;

    private static final String TAG = "StatusFragment";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "password";

    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;
    private int maxCount;

    private class StatusActivityOnClickListener implements View.OnClickListener {
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

    private class StatusActivityTextCountTextWatcher implements TextWatcher {

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

    public static Context getFragmentContext() {
        return FRAGMENT_CONTEXT;
    }

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FRAGMENT_CONTEXT = this.getActivity();

        View view = inflater
                .inflate(R.layout.fragment_status, container, false);

        this.editStatus = (EditText) view.findViewById(R.id.editStatus);
        this.buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        this.textCount = (TextView) view.findViewById(R.id.textCount);
        this.defaultTextColor = textCount.getTextColors().getDefaultColor();

        Resources res = this.getResources();
        this.maxCount = res.getInteger(R.integer.max_text_count);

        this.buttonTweet.setOnClickListener(statusActivityOnClickListener);
        this.editStatus.addTextChangedListener(new StatusActivityTextCountTextWatcher());
        return view;
    }
}
