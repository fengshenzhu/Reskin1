package skin.lib;

/**
 * 动态添加View时关注View的属性
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
public class DynamicViewAttribute {
    String attrName;
    int resId;

    public DynamicViewAttribute(String attrName, int resId) {
        this.attrName = attrName;
        this.resId = resId;
    }
}
