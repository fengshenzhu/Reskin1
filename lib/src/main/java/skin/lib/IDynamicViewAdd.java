package skin.lib;

import android.view.View;

import java.util.List;

/**
 * 手动new View()支持换肤的接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
interface IDynamicViewAdd {
    /**
     * 添加View到换肤
     *
     * @param view  手动new View()
     * @param attrs 换肤时需要修改的属性
     */
    void addSkinView(View view, List<DynamicViewAttribute> attrs);
}
