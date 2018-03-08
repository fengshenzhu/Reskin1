package skin.lib.item;

import android.content.res.Resources;
import android.widget.AbsListView;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * AbsListView_listSelector换肤item,支持@drawable类型资源
 * 暂不支持@color形式
 * <p/>
 * Created by fengshzh on 16/2/28.
 */
public class ListViewListSelectorItem extends BaseSkinItem {
    private WeakReference<AbsListView> mView;

    public ListViewListSelectorItem(AbsListView view, int resId) {
        mView = new WeakReference<>(view);
        mResId = resId;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        AbsListView listView = mView.get();
        if (listView != null) {
            try {
                listView.setSelector(theme.getDrawable(mResId));
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
