package com.world.play.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.StyleRes;

import com.world.play.R;

public class ThemeUtils {
    private static int CURRENT_THEME;

    public final static int THEME_LIGHT = 1;
    public final static int THEME_DARK = 2;

    public static void changeTheme(Activity activity, int themeType) {
        CURRENT_THEME = themeType;

        activity.finish();
    }

    public static void updateTheme(Context context, int theme) {
        PreferenceUtils.getInstance().setTheme(context, theme);
    }

    @StyleRes
    public static int getThemeResFromPrefValue(int themePrefValue) {
        switch (themePrefValue) {
            case THEME_DARK:
                return R.style.Theme_Dark;
            case THEME_LIGHT:
            default:
                return R.style.Theme_Light;
        }
    }
}
