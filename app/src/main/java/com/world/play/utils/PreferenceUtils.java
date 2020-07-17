package com.world.play.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceUtils {

    private static PreferenceUtils preferenceUtils;

    private final String USER_DATA = "user_data";
    private final String CURRENT_THEME = "current_theme";

    private PreferenceUtils() {
        if (preferenceUtils != null) {
            throw new RuntimeException("Use getInstance() method to get Single Instance");
        }
    }

    public static PreferenceUtils getInstance() {
        if (preferenceUtils == null) {
            preferenceUtils = new PreferenceUtils();
        }
        return preferenceUtils;
    }

    public void setTheme(Context context, int theme) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CURRENT_THEME, theme);
        editor.apply();
    }

    public int getCurrentTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return preferences.getInt(CURRENT_THEME, ThemeUtils.THEME_LIGHT);
    }
}