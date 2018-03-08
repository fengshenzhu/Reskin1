package skin.lib.item;

import android.content.res.Resources;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * TextView_drawableLeft/drawableTop/drawableRight/drawableBottom换肤item,支持@drawable类型资源
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public class TextViewCompoundDrawablesItem extends BaseSkinItem {
    private WeakReference<TextView> mView;

    // drawable resource id, 值为-1时无效,更新CompoundDrawable时应传null
    private int mLeftDrawableResId;
    private int mTtopDrawableResId;
    private int mRightDrawableResId;
    private int mBottomDrawableResId;

    public TextViewCompoundDrawablesItem(TextView view, int leftDrawableResId, int
            topDrawableResId, int rightDrawableResId, int bottomDrawableResId) {
        mView = new WeakReference<>(view);
        mLeftDrawableResId = leftDrawableResId;
        mTtopDrawableResId = topDrawableResId;
        mRightDrawableResId = rightDrawableResId;
        mBottomDrawableResId = bottomDrawableResId;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        TextView textView = mView.get();
        if (textView != null) {
            try {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                        mLeftDrawableResId > 0 ? theme.getDrawable(mLeftDrawableResId) : null,
                        mTtopDrawableResId > 0 ? theme.getDrawable(mTtopDrawableResId) : null,
                        mRightDrawableResId > 0 ? theme.getDrawable(mRightDrawableResId) : null,
                        mBottomDrawableResId > 0 ? theme.getDrawable(mBottomDrawableResId) : null);
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
