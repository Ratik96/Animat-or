package com.ratik.animationsdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ratik.animationsdemo.service.DaydreamService;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private String[] quotes;

    protected TextView mQuotesTextView;
    protected RelativeLayout mMainLayout;
    protected Button mSettingsButton;

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        boolean daydream = getIntent().getBooleanExtra(DaydreamService.KEY_DAYDREAM, false);

        if(daydream) {
            mSettingsButton.setVisibility(View.INVISIBLE);
        }
        else {
            mSettingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("immersive", false)) {
            startImmersiveMode();
        }

        // Getting prefs
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int timerDelay = Integer.parseInt(sp.getString("timer_period", "2"));

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new InfinteLoopTask(), 500, timerDelay * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    private void init() {
        mQuotesTextView = (TextView) findViewById(R.id.quotesTextView);
        mMainLayout = (RelativeLayout) findViewById(R.id.main);
        mSettingsButton = (Button) findViewById(R.id.settingsButton);
        quotes = getResources().getStringArray(R.array.quotes);

        YoYo.with(Techniques.SlideInRight).duration(500).playOn(findViewById(R.id.settingsButton));
        setRandomBackgroundColor(mMainLayout);
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

    private void startRandomEnterAnimation(int id) {
        final int ENTER_ANIMATION_COUNT = 10;
        Random random = new Random();
        int randomNumber = random.nextInt(ENTER_ANIMATION_COUNT);
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

    private void startRandomExitAnimation(int id) {
        final int EXIT_ANIMATION_COUNT = 8;
        Random random = new Random();
        int randomNumber = random.nextInt(EXIT_ANIMATION_COUNT);
        switch (randomNumber) {
            case 0:
                YoYo.with(Techniques.RollOut).playOn(findViewById(id));
                break;
            case 1:
                YoYo.with(Techniques.FadeOut).duration(1000).playOn(findViewById(id));
                break;
            case 2:
                YoYo.with(Techniques.FlipOutX).playOn(findViewById(id));
                break;
            case 3:
                YoYo.with(Techniques.FlipOutY).playOn(findViewById(id));
                break;
            case 4:
                YoYo.with(Techniques.RotateOut).playOn(findViewById(id));
                break;
            case 5:
                YoYo.with(Techniques.SlideOutLeft).playOn(findViewById(id));
                break;
            case 6:
                YoYo.with(Techniques.SlideOutRight).playOn(findViewById(id));
                break;
            case 7:
                YoYo.with(Techniques.ZoomOut).playOn(findViewById(id));
                break;
        }
    }

    private void setRandomBackgroundColor(View v) {
        String[] colors = { "#6c8c9b", "#c32a29", "#4dab4f", "#4d65ab" };
        v.setBackgroundColor(Color.parseColor(colors[getRandomIndex(colors)]));
        v.startAnimation(new AlphaAnimation(0, 1));
    }

    private class InfinteLoopTask extends TimerTask {

        private final int NO_OF_QUOTES = quotes.length;
        int counter = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mQuotesTextView.setVisibility(View.INVISIBLE);
                mQuotesTextView.setText(quotes[counter - 1]);
                setRandomBackgroundColor(mMainLayout);
                mQuotesTextView.setVisibility(View.VISIBLE);
                startRandomEnterAnimation(R.id.quotesTextView);
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
