package com.ratik.animationsdemo;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    boolean mBindingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        // Hide Immersive mode setting if user's device is running API_VERSION < 19
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            PreferenceCategory category = (PreferenceCategory) findPreference("app_controls");
            CheckBoxPreference immersiveMode = (CheckBoxPreference) findPreference("immersive");
            category.removePreference(immersiveMode);
        }

        // Syncs up Preference's summary with the value entered/chosen
        bindPreferenceSummaryToValue(findPreference("timer_period"));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        mBindingPreference = true;

        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));

        mBindingPreference = false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        if (preference instanceof EditTextPreference) {
            preference.setSummary("Time period for quote change: " + stringValue);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
