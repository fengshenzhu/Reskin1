package skin.lib;

/**
 * 自定义View实现换肤的接口,支持换肤的自定义View需要实现此接口
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
public interface ICustomSkinView {
    /**
     * 初始化换肤关注的属性
     */
    void initSKinRes();

    /**
     * 自定义View换肤，注意，使用{@link SkinTheme}获取资源必须处理异常
     *
     * @param theme 当前主题，用于获取该主题下资源
     */
    void reSkin(SkinTheme theme);
}
