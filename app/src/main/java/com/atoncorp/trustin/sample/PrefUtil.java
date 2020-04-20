package com.atoncorp.trustin.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefUtil {
    private static PrefUtil instance;

    private SharedPreferences mPreferences;

    private Context mContext;

    private static final String PREF_KEY = "pref";

    public static final String FINGER_REG_YN = "FINGER_REG_YN";

    public static final String TAG = PrefUtil.class.getSimpleName();

    public PrefUtil(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public static PrefUtil getInstance(Context context) {
        if (instance == null) {
            return new PrefUtil(context);
        }
        return instance;
    }


    public void savePreferences(String key, String value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        Log.d(TAG, "[savePreferences] key ( " + key + " ), value ( " + value + " )");
        editor.commit();
    }

    public void savePreferences(String key, int value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        Log.d(TAG, "[savePreferences] key ( " + key + " ), value ( " + value + " )");
        editor.commit();
    }

    public String getPreferences(String key){
        Log.d(TAG, "[getPreferences] key ( " + key + " ), value ( " + mPreferences.getString(key, "") + " )");
        return mPreferences.getString(key, "");

    }


    public int getPreferencesInteger(String key){
        Log.d(TAG, "[getPreferences] key ( " + key + " ), value ( " + mPreferences.getInt(key, 0) + " )");
        return mPreferences.getInt(key, 0);

    }

    public void removePreference(String key) {
        Log.d(TAG, "[removePreference] key ( " + key + " ), value ( " + mPreferences.getString(key, "") + " )");
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
