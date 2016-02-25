package skin.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 原自定义View实现了{@link android.view.View#onDraw(Canvas)}的,继承此类
 * <p/>
 * 添加换肤支持只需实现{@link #setSkinTheme(SkinTheme)}方法,根据Theme初始化资源,在{@link #onDraw(Canvas)}设置资源
 * <p/>
 * 如:
 * int mColor;
 *
 * @Override protected void setSkinTheme(SkinTheme theme) {
 * mColor=theme.getColor(R.color.textColor);
 * }
 * @Override protected void onDraw(Canvas canvas) {
 * super.onDraw(canvas);
 * mPaint.setColor(mColor);
 * ...
 * }
 * <p/>
 * Created by fengshzh on 1/27/16.
 */

public abstract class CustomSkinView extends View implements ICustomSkinView {
    public CustomSkinView(Context context) {
        super(context);
        setSkinTheme(SkinManager.getTheme());
    }

    public CustomSkinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSkinTheme(SkinManager.getTheme());
    }

    public CustomSkinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSkinTheme(SkinManager.getTheme());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // TODO: fengshzh 16/2/24 getContext()可能不是Activity的Context么?
        ((BaseSkinActivity) getContext()).addCustomView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BaseSkinActivity) getContext()).removeCustomView(this);
    }

    @Override
    public void reSkin(SkinTheme theme) {
        setSkinTheme(theme);
        invalidate();
    }

    /**
     * 获取主题资源
     *
     * @param theme 主题
     */
    protected abstract void setSkinTheme(SkinTheme theme);
}
