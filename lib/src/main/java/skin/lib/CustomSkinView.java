package skin.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 原自定义View直接继承{@link android.view.View}的,可继承此View支持换肤
 * <p/>
 * 添加换肤支持只需修改{@link #onDraw(Canvas)},获取主题资源并设置
 * 如:
 * @Override protected void onDraw(Canvas canvas) {
 * SkinTheme theme = SkinManager.getTheme();
 * int color = theme.getColor(R.color.textColor);
 * mPaint.setColor(color);
 * ...
 * }
 * <p/>
 * Created by fengshzh on 1/27/16.
 */
public abstract class CustomSkinView extends View implements ICustomSkinView {
    public CustomSkinView(Context context) {
        super(context);
    }

    public CustomSkinView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSkinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((BaseSkinActivity) getContext()).addCustomView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BaseSkinActivity) getContext()).removeCustomView(this);
    }

    @Override
    public void reSkin(SkinTheme theme) {
        invalidate();
    }
}
