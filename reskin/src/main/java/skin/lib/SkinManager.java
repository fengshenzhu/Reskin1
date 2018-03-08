package skin.lib;

import android.content.Context;

/**
 * 换肤管理器
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class SkinManager {
    private static Context mContext;

    /**
     * 当前主题
     */
    private static SkinTheme mTheme;

    private SkinManager() {
    }

    /**
     * 初始化换肤库
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        SkinPreference.init(context.getApplicationContext());
        mTheme = SkinPreference.getTheme();
    }

    static Context getContext() {
        return mContext;
    }

    /**
     * 获取当前全局主题
     *
     * @return 当前全局主题
     */
    public static SkinTheme getTheme() {
        return mTheme;
    }

    /**
     * 设置全局主题
     * 注意: 换肤的Activity里如果有Fragment,Fragment的换肤将不会触发,显现出bug
     *
     * @param newTheme 换肤目标全局主题
     * @param activity 触发换肤所在Activity
     */
    public static void reSkin(SkinTheme newTheme, BaseSkinActivity activity) {
        if (newTheme == mTheme) {
            return;
        }
        mTheme = newTheme;

        activity.reSkin(mTheme);

        SkinPreference.setTheme(mTheme);
    }
}
