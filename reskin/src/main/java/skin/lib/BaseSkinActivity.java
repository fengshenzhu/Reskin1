package skin.lib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * 换肤基类Activity
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public abstract class BaseSkinActivity extends FragmentActivity implements IDynamicViewAdd,
        ICustomViewAdd {
    private static final String TAG = "BaseSkinActivity";

    private SkinLayoutInflaterFactory mSkinLayoutInflaterFactory;
    private SkinTheme mTheme = SkinTheme.DEFAULT; // 记录当前Activity的皮肤
    private boolean mIsFirstResume = true; // 记录onResume是否是第一次执行,第一次执行不需主动触发换肤

    private long mStartTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStartTime = System.currentTimeMillis();

        mSkinLayoutInflaterFactory = new SkinLayoutInflaterFactory(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        try {
            layoutInflater.setFactory(mSkinLayoutInflaterFactory);
        } catch (Exception e) {
            L.e(TAG, this.getClass().getSimpleName() + "SkinLayoutInflaterFactory not set!!!");
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        long a = System.currentTimeMillis();
        super.setContentView(layoutResID);
        L.d(TAG, this.getClass().getSimpleName() + " setContentView cost time: " + (System.currentTimeMillis() - a));
    }

    @Override
    protected void onResume() {
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
    protected void onDestroy() {
        super.onDestroy();
        if (mSkinLayoutInflaterFactory != null) {
            mSkinLayoutInflaterFactory.clear();
        }
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

    /**
     * 添加自定义View,包括Fragment里的
     *
     * @param view 自定义的View
     */
    @Override
    final public void addCustomView(ICustomSkinView view) {
        mSkinLayoutInflaterFactory.addCustomView(view);
    }

    /**
     * 移除自定义View,包括Fragment里的
     *
     * @param view 自定义的View
     */
    @Override
    final public void removeCustomView(ICustomSkinView view) {
        mSkinLayoutInflaterFactory.removeCustomView(view);
    }
}
