package com.example.nwhacks2019;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class final_activity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);
        Data data = Data.getInstance();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(data.getTextView());

    }




}
