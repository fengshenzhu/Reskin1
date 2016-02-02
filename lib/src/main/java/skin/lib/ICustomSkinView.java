package skin.lib;

/**
 * 自定义View实现换肤的接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
interface ICustomSkinView {
    /**
     * 自定义View换肤，注意，使用{@link SkinTheme#getId(int)}必须处理异常
     *
     * @param theme 当前主题，用于获取该主题下资源
     */
    void reSkin(SkinTheme theme);
}
