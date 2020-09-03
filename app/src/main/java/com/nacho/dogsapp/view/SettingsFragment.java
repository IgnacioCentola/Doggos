package com.nacho.dogsapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.nacho.dogsapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }
//    public static SettingsFragment newInstance() {
//        return new SettingsFragment();
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

}