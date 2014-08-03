package com.ratik.animationsdemo.helpers;

import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class Randomizer {

    public static int getRandomIndex(String[] array) {
        Random random = new Random();
        return random.nextInt(array.length);
    }

    public static void startRandomEnterAnimation(int id, View rootView, int duration) {
        final int ENTER_ANIMATION_COUNT = 10;
        Random random = new Random();
        int randomNumber = random.nextInt(ENTER_ANIMATION_COUNT);
        switch (randomNumber) {
            case 0:
                YoYo.with(Techniques.RubberBand).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 1:
                YoYo.with(Techniques.Wobble).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 2:
                YoYo.with(Techniques.Tada).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 3:
                YoYo.with(Techniques.Wave).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 4:
                YoYo.with(Techniques.BounceIn).playOn(rootView.findViewById(id));
                break;
            case 5:
                YoYo.with(Techniques.SlideInLeft).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 6:
                YoYo.with(Techniques.SlideInRight).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 7:
                YoYo.with(Techniques.SlideInUp).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 8:
                YoYo.with(Techniques.Shake).duration(duration).playOn(rootView.findViewById(id));
                break;
            case 9:
                YoYo.with(Techniques.RollIn).duration(duration).playOn(rootView.findViewById(id));
                break;
        }
    }

    public static void setRandomBackgroundColor(View v) {
        String[] colors = { "#6c8c9b", "#c32a29", "#4dab4f", "#4d65ab" };
        v.setBackgroundColor(Color.parseColor(colors[getRandomIndex(colors)]));
        v.startAnimation(new AlphaAnimation(0, 1));
    }
}
