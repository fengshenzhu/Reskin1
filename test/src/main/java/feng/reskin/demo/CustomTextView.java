package feng.reskin.demo;

import android.content.Context;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

import skin.lib.BaseSkinActivity;
import skin.lib.ICustomSkinView;
import skin.lib.SkinTheme;
import skin.test.R;

/**
 * 自定义View,未自行实现onDraw()
 * <p/>
 * Created by fengshzh on 16/2/17.
 */
public class CustomTextView extends TextView implements ICustomSkinView {

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAutoLinkMask(Linkify.WEB_URLS);
        setText("github: www.github.com");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /** 添加换肤管理 */
        ((BaseSkinActivity)getContext()).addCustomView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /** 移除换肤管理 */
        try {
            ((BaseSkinActivity)getContext()).removeCustomView(this);
        } catch (NullPointerException e) {
        }
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
        int mColor = theme.getColor(R.color.textColor);
        /** 2. 设置主题资源 */
        setLinkTextColor(mColor);
    }

}
