package skin.lib;

import android.graphics.Canvas;

/**
 * 自定义View实现换肤的接口,支持换肤的自定义View需要实现此接口
 * <p/>
 * 1. 原View实现了{@link android.view.View#onDraw(Canvas)},继承{@link CustomSkinView}实现换肤
 * <p/>
 * 2. 其它,需实现此接口,并实现:
 * (1). 在{@link android.view.ViewGroup#onAttachedToWindow()}添加View到换肤管理,并初始化View的主题
 * 如:
 * @Override protected void onAttachedToWindow() {
 * super.onAttachedToWindow();
 * ((BaseSkinActivity) getContext()).addCustomView(this);
 * reSkin(SkinManager.getTheme());
 * }
 * <p/>
 * (2). 在{@link android.view.ViewGroup#onDetachedFromWindow()}将View从换肤管理中移除
 * 如:
 * @Override protected void onDetachedFromWindow() {
 * super.onDetachedFromWindow();
 * ((BaseSkinActivity) getContext()).removeCustomView(this);
 * }
 * <p/>
 * (3). 实现{@link #reSkin(SkinTheme)}方法,获取主题资源并设置
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
