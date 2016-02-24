package skin.lib.item;

import skin.lib.SkinTheme;

/**
 * 换肤view及其属性记录的基类
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public abstract class BaseSkinItem {
    /**
     * 换肤支持的资源类型
     */
    protected static final String RES_DRAWABLE = "drawable";
    protected static final String RES_COLOR = "color";

    public int resId;

    public abstract void reSkin(SkinTheme theme);
}
