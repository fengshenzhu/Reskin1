package skin.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * 换肤基类Fragment
 * 自定义View的换肤管理不放在Fragment里,放在所属Activity里.
 * <p/>
 * Created by fengshzh on 16/2/18.
 */
public abstract class BaseSkinFragment extends Fragment implements IDynamicViewAdd {
    private static final String TAG = "BaseSkinFragment";
    private long mStartTime = 0;
    private LayoutInflater mLayoutInflater;
    private SkinLayoutInflaterFactory mSkinLayoutInflaterFactory;

    private SkinTheme mTheme = SkinTheme.DEFAULT; // 记录当前Activity的皮肤
    private boolean mIsFirstResume = true; // 记录onResume是否是第一次执行,第一次执行不需主动触发换肤


    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        if (mLayoutInflater == null) {
            mLayoutInflater = getActivity().getLayoutInflater().cloneInContext(getActivity());
            mSkinLayoutInflaterFactory = new SkinLayoutInflaterFactory(this, mLayoutInflater);
            try {
                // 参考: super.getLayoutInflater()
                mLayoutInflater.setFactory(mSkinLayoutInflaterFactory);
                getChildFragmentManager();
            } catch (Exception e) {
                L.e(TAG, this.getClass().getSimpleName() + "SkinLayoutInflaterFactory not set!!!");
            }
        }
        return mLayoutInflater;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartTime = System.currentTimeMillis();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsFirstResume) {
            mIsFirstResume = false;
            if (mTheme != SkinManager.getTheme()) {
                // 第一次只更新theme,不刷新UI
                mTheme = SkinManager.getTheme();
            }
        } else {
            if (mTheme != SkinManager.getTheme()) {
                // 之后每次都更新theme,刷新UI
                mTheme = SkinManager.getTheme();
                reSkin(mTheme);
            }
        }
        if (mStartTime != 0) {
            L.d(TAG, this.getClass().getSimpleName() + " resume cost time: " + (System
                    .currentTimeMillis() - mStartTime));
            mStartTime = 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSkinLayoutInflaterFactory.clear();
    }

    /**
     * 当前Activity换肤
     */
    public void reSkin(SkinTheme theme) {
        long aTime = System.currentTimeMillis();
        mTheme = theme;
        mSkinLayoutInflaterFactory.reSkin(theme);
        L.d(TAG, this.getClass().getSimpleName() + " reSkin cost time: " + (System
                .currentTimeMillis() - aTime));
    }

    /**
     * 手动添加View
     *
     * @param view  手动new View()
     * @param attrs 换肤时需要修改的属性
     */
    @Override
    final public void addSkinView(View view, List<DynamicViewAttribute> attrs) {
        mSkinLayoutInflaterFactory.addSkinViewIfNecessary(view, attrs);
    }
}
