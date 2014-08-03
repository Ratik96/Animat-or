package com.ratik.animationsdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ratik.animationsdemo.helpers.Randomizer;
import com.ratik.animationsdemo.service.DaydreamService;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private String[] quotes;

    protected TextView mQuotesTextView;
    protected RelativeLayout mMainLayout;
    protected Button mSettingsButton;

    private Timer mTimer;

    private boolean isButtonVisible;

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

        mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isButtonVisible) {
                    YoYo.with(Techniques.SlideInRight).duration(500).playOn(findViewById(R.id.settingsButton));
                    isButtonVisible = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("immersive", false)) {
            startImmersiveMode();
        }

        // Getting prefs
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int timerDelay = Integer.parseInt(sp.getString("timer_period", "3"));

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new InfinteLoopTask(), 500, timerDelay * 1000);

        // Dismiss Settings Button after 5 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.SlideOutRight).playOn(findViewById(R.id.settingsButton));
                isButtonVisible = false;
            }
        }, 5 * 1000);
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

        mQuotesTextView.setText(quotes[0]);

        YoYo.with(Techniques.SlideInRight).duration(500).playOn(findViewById(R.id.settingsButton));
        Randomizer.setRandomBackgroundColor(mMainLayout);
    }

    @TargetApi(19)
    public void startImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private class InfinteLoopTask extends TimerTask {
        int NO_OF_QUOTES = quotes.length;
        int counter = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                int timerDelay = Integer.parseInt(sp.getString("timer_period", "3"));

                final Animation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
                fadeOutAnimation.setDuration((timerDelay * 1000) - ((timerDelay * 1000) - 1000));

                fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Nothing here
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mQuotesTextView.setText(quotes[counter]);
                        Randomizer.setRandomBackgroundColor(mMainLayout);
                        Randomizer.startRandomEnterAnimation(R.id.quotesTextView, mMainLayout, 1000);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Nothing here
                    }
                });
                mQuotesTextView.startAnimation(fadeOutAnimation);
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
