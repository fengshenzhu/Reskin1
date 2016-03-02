package skin.lib;

/**
 * 系统原始View手动addView()时关注View的属性
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
public class DynamicViewAttribute {
    String mAttrName;
    int mResId;

    /**
     * @param attrName 属性名,见{@link SkinLayoutInflaterFactory#ATTR_VIEW_BACKGROUND}等
     *                 暂不支持TextView的drawableLeft/drawableTop/drawableRight/drawableBottom
     * @param resId 默认主题的资源id
     */
    public DynamicViewAttribute(String attrName, int resId) {
        this.mAttrName = attrName;
        this.mResId = resId;
    }
}
