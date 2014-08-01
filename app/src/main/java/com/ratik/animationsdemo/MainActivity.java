package com.ratik.animationsdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends Activity {

    private final int ANIMATION_COUNT = 10;

    /*  TODO
        1) MainActivity UX? — randomize everything; color, text, font?
        2) Make a Daydream applet
     */

    private String[] quotes;

    protected TextView mQuotesTextView;
    protected Button mNextButton;
    protected RelativeLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuotesTextView.setAlpha(0f);

                mQuotesTextView.setText(quotes[getRandomIndex(quotes)]);
                startRandomAnimation(R.id.quotesTextView);
                setRandomBackgroundColor(mMainLayout);
                // setRandomFont();
            }
        });

    }

    private void init() {
        mQuotesTextView = (TextView) findViewById(R.id.quotesTextView);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mMainLayout = (RelativeLayout) findViewById(R.id.main);
        quotes = getResources().getStringArray(R.array.quotes);

        mQuotesTextView.setText(quotes[getRandomIndex(quotes)]);
        YoYo.with(Techniques.RotateIn).playOn(findViewById(R.id.quotesTextView));

        setRandomBackgroundColor(mMainLayout);
        // setRandomFont();
    }

    private int getRandomIndex(String[] array) {
        Random random = new Random();
        return random.nextInt(array.length);
    }

    private void startRandomAnimation(int id) {
        Random random = new Random();
        int randomNumber = random.nextInt(ANIMATION_COUNT) + 1;
        int viewId = id;
        switch (randomNumber) {
            case 1:
                YoYo.with(Techniques.RubberBand).playOn(findViewById(id));
                break;
            case 2:
                YoYo.with(Techniques.Wobble).playOn(findViewById(id));
                break;
            case 3:
                YoYo.with(Techniques.Tada).playOn(findViewById(id));
                break;
            case 4:
                YoYo.with(Techniques.Wave).playOn(findViewById(id));
                break;
            case 5:
                YoYo.with(Techniques.BounceIn).playOn(findViewById(id));
                break;
            case 6:
                YoYo.with(Techniques.SlideInLeft).playOn(findViewById(id));
                break;
            case 7:
                YoYo.with(Techniques.SlideInRight).playOn(findViewById(id));
                break;
            case 8:
                YoYo.with(Techniques.SlideInUp).playOn(findViewById(id));
                break;
            case 9:
                YoYo.with(Techniques.Shake).playOn(findViewById(id));
                break;
            case 10:
                YoYo.with(Techniques.RollIn).playOn(findViewById(id));
                break;
        }
    }

    private void setRandomBackgroundColor(View v) {
        // TODO: avoid getting colors from resources
        String[] colors = getResources().getStringArray(R.array.colors);
        v.setBackgroundColor(Color.parseColor(colors[getRandomIndex(colors)]));
        v.startAnimation(new AlphaAnimation(0, 1));
    }

//    private void setRandomFont() {
//        String fonts[] = {"action_comics.ttf", "arista.ttf", "badabb.ttf", "obelix.ttf", "shabby.ttf"};
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/" + fonts[getRandomIndex(fonts)]);
//
//        String quoteText = mQuotesTextView.getText().toString();
//        String[] quote = quoteText.split(" ");
//        for (String q : quote) {
//            if (q.length() > 6) {
//                // Set custom font
//                TextView styledTextView = new TextView(this);
//                styledTextView.setText(q);
//                styledTextView.setTextSize(40);
//                styledTextView.setGravity(Gravity.CENTER);
//                styledTextView.setTextColor(Color.parseColor("#ffffff"));
//                styledTextView.setTypeface(tf);
//                mMainLayout.addView(styledTextView);
//                break;
//            }
//        }
//    }
}
