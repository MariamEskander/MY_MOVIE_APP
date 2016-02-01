package com.example.android.movie.app;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by lenovo 2 on 1/26/2016.
 */
public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        bindPreferenceSummaryToValue(findPreference("sortby"));


        }

private void  bindPreferenceSummaryToValue(Preference preference){

        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(), ""));
        }



@Override
public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if(preference instanceof ListPreference){

        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(stringValue);
        if(prefIndex >=0)
        {
        preference.setSummary(listPreference.getEntries()[prefIndex]);
        }
        else
        {
        preference.setSummary(stringValue);
        }

        }

        return true;
        }
        }
