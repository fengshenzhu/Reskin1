package skin.lib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

/**
 * 换肤基类Activity
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public abstract class BaseSkinActivity extends FragmentActivity implements IDynamicViewAdd, ICustomViewAdd {
    private SkinLayoutInflaterFactory skinLayoutInflaterFactory;
    private SkinTheme theme = SkinTheme.DEFAULT; // 记录当前Activity的皮肤

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(this);
        getLayoutInflater().setFactory(skinLayoutInflaterFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (theme != SkinManager.getTheme()) {
            theme = SkinManager.getTheme();
            reSkin(theme);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        skinLayoutInflaterFactory.clear();
    }

    /**
     * 当前Activity换肤
     */
    public void reSkin(SkinTheme theme) {
        skinLayoutInflaterFactory.reSkin(theme);
    }

    /**
     * 手动添加View
     * @param view  手动new View()
     * @param attrs 换肤时需要修改的属性
     */
    @Override
    final public void addSkinView(View view, List<DynamicViewAttribute> attrs) {
        skinLayoutInflaterFactory.addSkinViewIfNecessary(view, attrs);
    }

    /**
     * 添加自定义View
     * @param view 自定义的View
     */
    @Override
    final public void addCustomView(ICustomSkinView view) {
        skinLayoutInflaterFactory.addCustomView(view);
    }

    /**
     * 移除自定义View
     * @param view 自定义的View
     */
    @Override
    final public void removeCustomView(ICustomSkinView view) {
        skinLayoutInflaterFactory.removeCustomView(view);
    }
}
