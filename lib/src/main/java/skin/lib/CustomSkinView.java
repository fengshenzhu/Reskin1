package skin.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 支持换肤的自定义View基类，自定义View需实现{@link #reSkin(SkinTheme)}
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

        ((BaseActivity) getContext()).addCustomView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        ((BaseActivity) getContext()).removeCustomView(this);
    }

    @Override
    public abstract void reSkin(SkinTheme theme);
}
