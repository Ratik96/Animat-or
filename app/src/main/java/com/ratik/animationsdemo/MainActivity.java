package com.ratik.animationsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends Activity {

    /*  TODO
        1) MainActivity UX? — maybe use two pane fragment based UX
        2) Make a Daydream applet
     */

    private String[] quotes;

    protected TextView mQuotesTextView;
    protected Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mQuotesTextView.setText(quotes[getRandomIndex()]);
        YoYo.with(Techniques.RotateIn).playOn(findViewById(R.id.quotesTextView));

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuotesTextView.setAlpha(0f);

                mQuotesTextView.setText(quotes[getRandomIndex()]);
                YoYo.with(Techniques.RotateIn).playOn(findViewById(R.id.quotesTextView));
            }
        });

    }

    private void init() {
        mQuotesTextView = (TextView) findViewById(R.id.quotesTextView);
        mNextButton = (Button) findViewById(R.id.nextButton);
        quotes = getResources().getStringArray(R.array.quotes);
    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(quotes.length);
    }
}
