package skin.lib;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 换肤基类Fragment
 * <p/>
 * Created by fengshzh on 16/2/18.
 */
public class BaseSkinFragment extends Fragment {
    private boolean shouldReskin = false; // 记录Fragment创建后需要换肤否,仅用一次

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
            ((BaseSkinActivity) getActivity()).reSkin(SkinManager.getTheme());
        }
    }
}
