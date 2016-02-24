package skin.lib.item;

import android.content.res.Resources;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import skin.lib.SkinTheme;

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

    @Override
    public void reSkin(SkinTheme theme) {
        TextView textView = view.get();
        if (textView != null) {
            try {
                // TODO: fengshzh 16/2/23 拼成setTextColor(ColorStateList)?
                textView.setTextColor(theme.getColor(resId));
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
