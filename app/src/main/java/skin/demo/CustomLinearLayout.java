package skin.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.graphics.Canvas;

import skin.lib.BaseSkinActivity;
import skin.lib.ICustomSkinView;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 自定义View,未实现{@link android.view.View#onDraw(Canvas)}
 * <p/>
 * Created by fengshzh on 16/2/17.
 */
public class CustomLinearLayout extends LinearLayout implements ICustomSkinView {
    private int mColor;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /** 1. 添加当前View到换肤管理 */
        ((BaseSkinActivity) getContext()).addCustomView(this);
        /** 2. 初始化主题 */
        reSkin(SkinManager.getTheme());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BaseSkinActivity) getContext()).removeCustomView(this);
    }

    /**
     * 自行实现换肤,通过theme获取主题资源
     * 步骤:
     * 1. 获取主题资源
     * 2. 设置主题资源
     */
    @Override
    public void reSkin(SkinTheme theme) {
        /** 1. 获取主题资源 */
        mColor = theme.getColor(R.color.textColor);
        /** 2. 设置主题资源 */
        setBackgroundColor(mColor);
    }

}
