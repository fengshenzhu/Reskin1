package skin.lib.item;

import android.content.res.Resources;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * TextView_textColor换肤item,支持@color类型资源
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class TextViewTextColorItem extends BaseSkinItem {
    private WeakReference<TextView> mView;

    public TextViewTextColorItem(TextView view, int resId) {
        mView = new WeakReference<>(view);
        mResId = resId;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        TextView textView = mView.get();
        if (textView != null) {
            try {
                textView.setTextColor(theme.getColorStateList(mResId));
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
