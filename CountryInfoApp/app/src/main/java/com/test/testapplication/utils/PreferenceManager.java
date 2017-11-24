package com.test.testapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by moseskesavan on 11/24/17.
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
     * Constant Key for App File Search
     */
    public static final String APP_FILE_SEARCH_TEXT = "app_files_search_text";
    public static final String UPLOAD_PAUSED_STATE = "file_upload_pause_state";

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
        mPrefs.edit().clear().commit();
    }

    /**
     * Removes the.
     *
     * @param item the item
     */
    public void remove(String item) {
        mPrefs.edit().remove(item);
    }

    /**
     * Put int.
     *
     * @param key   the key
     * @param value the value
     */
    public void putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
    }

    /**
     * Gets the int.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the int
     */
    public int getInt(String key, int defaultValue) {
        return mPrefs.getInt(key, defaultValue);
    }

    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     */
    public void putString(String key, String value) {
        mPrefs.edit().putString(key, value).commit();
    }

    /**
     * Gets the string.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the string
     */
    public String getString(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    /**
     * Put boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).commit();
    }

    /**
     * Gets the boolean.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the boolean
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        return mPrefs.getBoolean(key, defaultValue);
    }

    /**
     * Put long.
     *
     * @param key   the key
     * @param value the value
     */
    public void putLong(String key, long value) {
        mPrefs.edit().putLong(key, value).commit();
    }

    /**
     * Gets the long.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the long
     */
    public long getLong(String key, long defaultValue) {
        return mPrefs.getLong(key, defaultValue);
    }

    /**
     * put Set<String>.
     *
     * @param key  the key
     * @param set  the value
     */
    public void putStringSet(String key, Set<String> set) {
        mPrefs.edit().putStringSet(key, set).commit();
    }

    public Set<String> getStringSet(String key) {
        return mPrefs.getStringSet(key, null);
    }
}
