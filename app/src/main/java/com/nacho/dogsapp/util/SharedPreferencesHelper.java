package com.nacho.dogsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferencesHelper {
    private static final String PREF_TIME = "Pref time";
    private static SharedPreferencesHelper instance;
    private SharedPreferences preferences;

    private SharedPreferencesHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    /**
     * Updates the cache duration that determines when to fetch from api
     *
     * @param time the time to set the current cache duration to
     */
    public void setUpdateTime(long time) {
        preferences.edit().putLong(PREF_TIME, time).apply();
    }

    /**
     * Return current cache duration
     *
     * @return current cache duration
     */

    public long getUpdateTime() {
        return preferences.getLong(PREF_TIME, 0);
    }

    /**
     * Retrieves cache duration in seconds from settings input
     *
     * @return cache duration in seconds in string format
     */
    public String getCacheDuration() {
        return preferences.getString("pref_cache_duration", "");
    }
}
