package skin.lib;

import android.content.Context;

/**
 * 持久化存储当前主题
 * <p/>
 * Created by fengshzh on 1/26/16.
 */
class SkinPreference {
    private static Context mContext;
    private static final String PREF_FILE = "theme";
    private static final String KEY_THEME = "key_theme";

    static void init(Context applicatonContext) {
        mContext = applicatonContext;
    }

    /**
     * 获取当前主题
     */
    static SkinTheme getTheme() {
        return SkinTheme.values()[mContext
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
                .getInt(KEY_THEME, SkinTheme.DEFAULT.ordinal())];
    }

    /**
     * 存储新主题
     *
     * @param skinTheme 新主题
     */
    static void setTheme(SkinTheme skinTheme) {
        mContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit()
                .putInt(KEY_THEME, skinTheme.ordinal())
                .apply();
    }
}
