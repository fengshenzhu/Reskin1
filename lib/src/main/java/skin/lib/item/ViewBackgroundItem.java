package skin.lib.item;

import android.content.res.Resources;
import android.view.View;

import java.lang.ref.WeakReference;

import skin.lib.SkinTheme;

/**
 * View background换肤item
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class ViewBackgroundItem extends BaseSkinItem {
    public WeakReference<View> view;
    public String typeName;

    public ViewBackgroundItem(View view, int resId, String typeName) {
        this.view = new WeakReference<>(view);
        this.resId = resId;
        this.typeName = typeName;
    }

    @Override
    public void reSkin(SkinTheme theme) {
        View view = this.view.get();
        if (view != null) {
            if (typeName.equals(RES_COLOR)) {
                try {
                    view.setBackgroundColor(theme.getColor(resId));
                } catch (Resources.NotFoundException e) {
                }
            } else if (typeName.equals(RES_DRAWABLE)) {
                try {
                    view.setBackgroundDrawable(theme.getDrawable(resId));
                } catch (Resources.NotFoundException e) {
                }
            }
        }
    }
}
