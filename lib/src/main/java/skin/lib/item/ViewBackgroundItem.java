package skin.lib.item;

import android.content.res.Resources;
import android.view.View;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * View_background换肤item,支持@color/@drawable类型资源
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class ViewBackgroundItem extends BaseSkinItem {
    private WeakReference<View> mView;
    private String mTypeName;

    public ViewBackgroundItem(View view, int resId, String typeName) {
        mView = new WeakReference<>(view);
        mResId = resId;
        mTypeName = typeName;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        View view = mView.get();
        if (view != null) {
            if (mTypeName.equals(RES_COLOR)) {
                try {
                    view.setBackgroundColor(theme.getColor(mResId));
                } catch (Resources.NotFoundException e) {
                }
            } else if (mTypeName.equals(RES_DRAWABLE)) {
                try {
                    view.setBackgroundDrawable(theme.getDrawable(mResId));
                } catch (Resources.NotFoundException e) {
                }
            }
        }
    }
}
