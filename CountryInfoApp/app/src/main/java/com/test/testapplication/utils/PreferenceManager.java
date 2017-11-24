package com.test.testapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by moseskesavan on 11/24/17.
 * Manager class will contain methods to save and retrieve data from/to the preference.
 */

public class PreferenceManager {

    /**
     * The m instance.
     */
    private static PreferenceManager mInstance = null;

    /**
     * The Constant PREFS_NAME.
     */
    private static final String PREFS_NAME = AppConstants.PREF_NAME;

    /**
     * The m prefs.
     */
    private SharedPreferences mPrefs;

    /**
     * Instantiates a new preference manager.
     *
     * @param context the context
     */
    private PreferenceManager(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets the single instance of PreferenceManager.
     *
     * @param context the context
     * @return single instance of PreferenceManager
     */
    public static PreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenceManager(context);
        }
        return mInstance;
    }

    /**
     * Clear.
     */
    public void clear() {
        mPrefs.edit().clear().apply();
    }

    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     */
    public void putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    /**
     * Gets the string.
     *
     * @param key          the key
     * @return the string
     */
    public String getString(String key) {
        return mPrefs.getString(key, "");
    }

    /**
     * Put boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
    }

    /**
     * Gets the boolean.
     *
     * @param key          the key
     * @return the boolean
     */
    public Boolean getBoolean(String key) {
        return mPrefs.getBoolean(key, false);
    }

}
