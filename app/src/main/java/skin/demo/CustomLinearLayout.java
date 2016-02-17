package skin.demo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import skin.lib.BaseSkinActivity;
import skin.lib.ICustomSkinView;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 自定义View,继承任何View/ViewGroup都支持换肤
 * <p/>
 * Created by fengshzh on 16/2/17.
 */
public class CustomLinearLayout extends LinearLayout implements ICustomSkinView {
    private int mColor;

    public CustomLinearLayout(Context context) {
        super(context);
        initSKinRes();
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSKinRes();
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSKinRes();
    }

    @Override
    public void initSKinRes() {
        mColor = getResources().getColor(R.color.textColor);

        if (SkinManager.getTheme() != SkinTheme.DEFAULT) {
            try {
                mColor = SkinManager.getTheme().getColor(R.color.textColor);
            } catch (Resources.NotFoundException e) {
            }
        }

        setBackgroundColor(mColor);
    }

    @Override
    public void reSkin(SkinTheme theme) {
        try {
            mColor = theme.getColor(R.color.textColor);
        } catch (Resources.NotFoundException e) {
        }
        setBackgroundColor(mColor);
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
}
