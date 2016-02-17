package skin.lib;

import android.content.Context;

/**
 * 换肤管理器
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class SkinManager {
    private static Context context;

    /**
     * 当前主题
     */
    private static SkinTheme theme;

    /**
     * 初始化换肤库
     *
     * @param context
     */
    public static void init(Context context) {
        SkinManager.context = context;
        SkinPreference.init(context.getApplicationContext());
        theme = SkinPreference.getTheme();
    }

    static Context getContext() {
        return context;
    }

    /**
     * 获取当前全局主题
     *
     * @return 当前全局主题
     */
    public static SkinTheme getTheme() {
        return theme;
    }

    /**
     * 设置全局主题
     *
     * @param newTheme 换肤目标全局主题
     */
    public static void reSkin(SkinTheme newTheme) {
        if (newTheme == theme) {
            return;
        }

        theme = newTheme;
        SkinPreference.setTheme(theme);
    }
}
