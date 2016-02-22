package skin.lib.item;

import android.widget.TextView;

import java.lang.ref.WeakReference;

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
}
