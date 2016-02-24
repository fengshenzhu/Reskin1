package skin.lib;

/**
 * 自定义View实现换肤的接口,支持换肤的自定义View需要实现此接口
 * <p/>
 * 1. 原View直接继承{@link android.view.View},继承{@link CustomSkinView}实现换肤
 * <p/>
 * 2. 原View非直接继承{@link android.view.View},需实现此接口,并实现以下:
 * a. 在{@link android.view.ViewGroup#onAttachedToWindow()}添加View到换肤管理,并初始化View的主题
 * 如:
 * @Override protected void onAttachedToWindow() {
 * super.onAttachedToWindow();
 * ((BaseSkinActivity) getContext()).addCustomView(this);
 * reSkin(SkinManager.getTheme());
 * }
 * <p/>
 * b. 在{@link android.view.ViewGroup#onDetachedFromWindow()}将View从换肤管理中移除
 * 如:
 * @Override protected void onDetachedFromWindow() {
 * super.onDetachedFromWindow();
 * ((BaseSkinActivity) getContext()).removeCustomView(this);
 * }
 * <p/>
 * c. 实现{@link #reSkin(SkinTheme)}方法,获取主题资源并设置
 * 如:
 * @Override public void reSkin(SkinTheme theme) {
 * int mColor = theme.getColor(R.color.textColor);
 * setBackgroundColor(mColor);
 * }
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
public interface ICustomSkinView {
    /**
     * 自定义View换肤
     *
     * @param theme 当前主题，用于获取该主题下资源
     */
    void reSkin(SkinTheme theme);
}
