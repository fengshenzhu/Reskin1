package skin.lib.item;

import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * TextView textColor属性换肤item
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class TextViewTextColorItem extends BaseSkinItem {
    public WeakReference<TextView> view;

    public TextViewTextColorItem(TextView view, int resId) {
        this.view = new WeakReference<>(view);
        this.resId = resId;
    }
}
