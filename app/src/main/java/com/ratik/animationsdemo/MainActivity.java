package com.ratik.animationsdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private final int ANIMATION_COUNT = 10;

    /*  TODO
        1) MainActivity UX? — randomize everything; color, text, font?
        2) Make a Daydream applet
     */

    private String[] quotes;

    protected TextView mQuotesTextView;
    protected RelativeLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startImmersiveMode();
    }

    private void init() {
        mQuotesTextView = (TextView) findViewById(R.id.quotesTextView);
        mMainLayout = (RelativeLayout) findViewById(R.id.main);
        quotes = getResources().getStringArray(R.array.quotes);

        setRandomBackgroundColor(mMainLayout);

        new Timer().scheduleAtFixedRate(new InfinteLoopTask(), 500, 3 * 1000);
    }

    @TargetApi(19)
    public void startImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private int getRandomIndex(String[] array) {
        Random random = new Random();
        return random.nextInt(array.length);
    }

    private void startRandomAnimation(int id) {
        Random random = new Random();
        int randomNumber = random.nextInt(ANIMATION_COUNT);
        int viewId = id;
        switch (randomNumber) {
            case 0:
                YoYo.with(Techniques.RubberBand).playOn(findViewById(id));
                break;
            case 1:
                YoYo.with(Techniques.Wobble).playOn(findViewById(id));
                break;
            case 2:
                YoYo.with(Techniques.Tada).playOn(findViewById(id));
                break;
            case 3:
                YoYo.with(Techniques.Wave).playOn(findViewById(id));
                break;
            case 4:
                YoYo.with(Techniques.BounceIn).playOn(findViewById(id));
                break;
            case 5:
                YoYo.with(Techniques.SlideInLeft).playOn(findViewById(id));
                break;
            case 6:
                YoYo.with(Techniques.SlideInRight).playOn(findViewById(id));
                break;
            case 7:
                YoYo.with(Techniques.SlideInUp).playOn(findViewById(id));
                break;
            case 8:
                YoYo.with(Techniques.Shake).playOn(findViewById(id));
                break;
            case 9:
                YoYo.with(Techniques.RollIn).playOn(findViewById(id));
                break;
        }
    }

    private void setRandomBackgroundColor(View v) {
        String[] colors = { "#6c8c9b", "#c32a29", "#4dab4f", "#4d65ab" };
        v.setBackgroundColor(Color.parseColor(colors[getRandomIndex(colors)]));
        v.startAnimation(new AlphaAnimation(0, 1));
    }

    private class InfinteLoopTask extends TimerTask {
        private final String TAG = InfinteLoopTask.class.getSimpleName();

        private final int NO_OF_QUOTES = quotes.length;
        int counter = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mQuotesTextView.setAlpha(0f);
                mQuotesTextView.setText(quotes[counter - 1]);
                startRandomAnimation(R.id.quotesTextView);
                setRandomBackgroundColor(mMainLayout);
            }
        };

        @Override
        public void run() {
            if (counter < NO_OF_QUOTES) {
                counter++;
                MainActivity.this.runOnUiThread(runnable);
            }
            else {
                counter = 0;
            }
        }
    }
}
