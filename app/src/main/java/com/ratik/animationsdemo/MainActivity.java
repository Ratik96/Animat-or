package com.ratik.animationsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private String[] quotes;
    protected TextView mQuotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        mQuotesTextView.setText(quotes[0]);
    }

    private void init() {
        mQuotesTextView = (TextView) findViewById(R.id.quotesTextView);
        quotes = getResources().getStringArray(R.array.quotes);
    }
}
