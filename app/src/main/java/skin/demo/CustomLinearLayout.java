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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((BaseSkinActivity) getContext()).addCustomView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BaseSkinActivity) getContext()).removeCustomView(this);
    }

    /**
     * 初始化换肤关注的属性
     */
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

    /**
     * 自定义View换肤，注意，使用{@link SkinTheme#getId(int)}必须处理异常
     *
     * @param theme 当前主题，用于获取该主题下资源
     */
    @Override
    public void reSkin(SkinTheme theme) {
        mColor = theme.getColor(R.color.textColor);
        setBackgroundColor(mColor);
    }


}
