package skin.lib;

/**
 * 管理支持换肤的自定义View接口,有自定义View的Activity需实现此接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
interface ICustomViewAdd {
    /**
     * 添加View到换肤管理
     *
     * @param view 自定义的View
     */
    void addCustomView(ICustomSkinView view);

    void removeCustomView(ICustomSkinView view);
}
