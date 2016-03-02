package skin.lib.item;

import android.content.res.Resources;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * ImageView_src换肤item,支持@drawable类型资源
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class ImageViewSrcItem extends BaseSkinItem {
    private WeakReference<ImageView> mView;

    public ImageViewSrcItem(ImageView view, int resId) {
        mView = new WeakReference<>(view);
        mResId = resId;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        ImageView imageView = mView.get();
        if (imageView != null) {
            try {
                imageView.setImageDrawable(theme.getDrawable(mResId));
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
