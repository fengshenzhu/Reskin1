package skin.lib.item;

import skin.lib.SkinTheme;

/**
 * Android原生View换肤item的基类,记录view及其属性
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public abstract class BaseSkinItem {
    /**
     * 原生View换肤支持的资源类型
     */
    protected static final String RES_DRAWABLE = "drawable";
    protected static final String RES_COLOR = "color";

    protected int mResId;

    private SkinTheme mTheme = SkinTheme.DEFAULT;

    final public void reSkinIfNecessary(SkinTheme theme) {
        if (mTheme != theme) {
            mTheme = theme;
            reSkin(mTheme);
        }
    }

    abstract void reSkin(SkinTheme theme);
}
