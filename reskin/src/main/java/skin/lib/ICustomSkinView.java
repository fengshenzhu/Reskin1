package skin.lib;

/**
 * 自定义View实现换肤的接口,支持换肤的自定义View需要实现此接口.
 * 确保自定义View构造函数传入的Context为其所属的Activity
 * 自定义View的换肤管理目前都在BaseSkinActivity里(),包括Activity里Fragment的自定义View.
 * <p/>
 * 使用方法:
 * (1). 在onAttachedToWindow()添加View到Activity的换肤管理.
 * 添加时会触发一次reSkin()方法,以实现当前View的主题初始化.
 * 如:
 * @Override protected void onAttachedToWindow() {
 * super.onAttachedToWindow();
 * ((BaseSkinActivity) getContext()).addCustomView(this);
 * }
 * <p/>
 * (2). 在onDetachedFromWindow()将View从Activity的换肤管理中移除
 * 移除时防止Activity已被回收需手动捕捉异常.
 * 如:
 * @Override protected void onDetachedFromWindow() {
 * super.onDetachedFromWindow();
 * try{
 * ((BaseSkinActivity) getContext()).removeCustomView(this);
 * } catch(NullPointException(){}
 * }
 * <p/>
 * (3). 实现{@link #reSkin(SkinTheme)}方法,获取主题资源并设置.
 * 此时分两种情况,一种是在reSkin()里可以设置换肤的,另一种是要通过自定义View的onDraw()才能设置换肤的.
 * 如:
 * a). 在reSkin()里设置
 * @Override public void reSkin(SkinTheme theme) {
 * int mColor = theme.getColor(R.color.textColor);
 * setBackgroundColor(mColor);
 * // 自行实现了onDraw()的自定义View调用invalidate()设置资源
 * }
 * b). onDraw()里设置
 * int mColor;
 * @Override public void reSkin(SkinTheme theme) {
 * int mColor = theme.getColor(R.color.textColor);
 * invalidate();
 * }
 * @Override protected void onDraw(Canvas canvas) {
 * super.onDraw(canvas);
 * // 使用mColor
 * ...
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
