package skin.lib.item;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import java.lang.ref.WeakReference;

import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * ListView_divider换肤item,支持@color类型资源
 * 暂不支持@drawable形式
 * <p/>
 * Created by fengshzh on 16/2/28.
 */
public class ListViewDividerItem extends BaseSkinItem {
    private WeakReference<ListView> mView;

    public ListViewDividerItem(ListView view, int resId) {
        mView = new WeakReference<>(view);
        mResId = resId;

        reSkinIfNecessary(SkinManager.getTheme());
    }

    @Override
    protected void reSkin(SkinTheme theme) {
        ListView listView = mView.get();
        if (listView != null) {
            try {
                int dividerHeight = listView.getDividerHeight();
                listView.setDivider(new ColorDrawable(theme.getColor(mResId)));
                listView.setDividerHeight(dividerHeight);
            } catch (Resources.NotFoundException e) {
            }
        }
    }
}
