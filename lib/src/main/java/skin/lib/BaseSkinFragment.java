package skin.lib;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 换肤基类Fragment
 * <p/>
 * Created by fengshzh on 16/2/18.
 */
public abstract class BaseSkinFragment extends Fragment {
    private boolean shouldReskin = false; // Fragment创建后是否需要换肤,仅用一次

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (SkinManager.getTheme() != SkinTheme.DEFAULT) {
            shouldReskin = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shouldReskin) {
            shouldReskin = false;
            ((BaseSkinActivity) getActivity()).reSkin(SkinManager.getTheme());
        }
    }
}
