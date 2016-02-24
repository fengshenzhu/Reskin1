package skin.lib.item;

import android.content.res.Resources;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import skin.lib.SkinTheme;

/**
 * TextView drawableLeft/drawableTop/drawableRight/drawableBottom属性换肤item
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class TextViewCompoundDrawablesItem extends BaseSkinItem {
    public WeakReference<TextView> view;

    // drawable resource id, 值为-1时无效,更新CompoundDrawable时应传null
    public int leftDrawableResId;
    public int topDrawableResId;
    public int rightDrawableResId;
    public int bottomDrawableResId;

    public TextViewCompoundDrawablesItem(TextView view, int leftDrawableResId, int
            topDrawableResId, int rightDrawableResId, int bottomDrawableResId) {
        this.view = new WeakReference<>(view);
        this.leftDrawableResId = leftDrawableResId;
        this.topDrawableResId = topDrawableResId;
        this.rightDrawableResId = rightDrawableResId;
        this.bottomDrawableResId = bottomDrawableResId;
    }

    @Override
    public void reSkin(SkinTheme theme) {
        TextView textView = view.get();
        if (textView != null) {
            try {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                        leftDrawableResId > 0 ? theme.getDrawable(leftDrawableResId) : null,
                        topDrawableResId > 0 ? theme.getDrawable(topDrawableResId) : null,
                        rightDrawableResId > 0 ? theme.getDrawable(rightDrawableResId) : null,
                        bottomDrawableResId > 0 ? theme.getDrawable(bottomDrawableResId) : null);
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
