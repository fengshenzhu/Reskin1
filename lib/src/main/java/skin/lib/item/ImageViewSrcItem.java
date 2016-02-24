package skin.lib.item;

import android.content.res.Resources;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import skin.lib.SkinTheme;

/**
 * ImageView src属性换肤item
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class ImageViewSrcItem extends BaseSkinItem {
    public WeakReference<ImageView> view;

    public ImageViewSrcItem(ImageView view, int resId) {
        this.view = new WeakReference<>(view);
        this.resId = resId;
    }

    @Override
    public void reSkin(SkinTheme theme) {
        ImageView imageView = view.get();
        if (imageView != null) {
            try {
                imageView.setImageDrawable(theme.getDrawable(resId));
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
