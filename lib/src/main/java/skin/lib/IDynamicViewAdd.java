package skin.lib;

import android.view.View;

import java.util.List;

/**
 * 管理支持换肤的手动new View()接口,有手动添加View的Activity需实现此接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
interface IDynamicViewAdd {
    /**
     * 添加View到换肤管理
     *
     * @param view  手动new View()
     * @param attrs 换肤时需要修改的属性
     */
    void addSkinView(View view, List<DynamicViewAttribute> attrs);
}
