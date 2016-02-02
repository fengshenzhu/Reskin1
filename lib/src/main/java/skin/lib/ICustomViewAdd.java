package skin.lib;

/**
 * 自定义View支持换肤的接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
interface ICustomViewAdd {
    /**
     * 添加View到换肤
     *
     * @param view 自定义的View
     */
    void addCustomView(CustomSkinView view);

    void removeCustomView(CustomSkinView view);
}
