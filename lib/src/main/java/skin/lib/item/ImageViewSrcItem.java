package skin.lib.item;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

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
}
