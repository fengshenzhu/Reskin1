package skin.lib;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
     * 添加到换肤管理器的Activity列表
     */
    private static List<WeakReference<BaseActivity>> activityList = new ArrayList<>();


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
     * 获取当前主题
     *
     * @return 当前主题
     */
    public static SkinTheme getTheme() {
        return theme;
    }

    /**
     * 更换皮肤主题
     *
     * @param newTheme 换肤目标主题
     */
    public static void reSkin(SkinTheme newTheme) {
        if (newTheme == theme) {
            return;
        }

        theme = newTheme;
        SkinPreference.setTheme(theme);

        for (WeakReference<BaseActivity> ref : activityList) {
            BaseActivity activity = ref.get();
            if (activity != null) {
                activity.reSkin(theme);
            }
        }
    }

    /**
     * 向换肤管理器注册Activity
     */
    static void register(BaseActivity activity) {
        activityList.add(new WeakReference<>(activity));
    }

    /**
     * 向换肤管理器反注册Activity
     */
    static void unRegister(BaseActivity activity) {
        for (WeakReference<BaseActivity> ref : activityList) {
            if (ref.get() != null && ref.get() == activity) {
                activityList.remove(ref);
                return;
            }
        }
    }

}
