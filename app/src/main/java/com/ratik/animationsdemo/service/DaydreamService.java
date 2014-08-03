package com.ratik.animationsdemo.service;

import android.content.Intent;
import android.service.dreams.DreamService;

import com.ratik.animationsdemo.MainActivity;

public class DaydreamService extends DreamService {

    public static final String KEY_DAYDREAM = "daydream";

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_DAYDREAM, true);
        startActivity(intent);

        finish();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();
        finish();
    }
}
