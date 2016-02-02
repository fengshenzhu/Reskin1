package skin.lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * 换肤基类Activity
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public abstract class BaseActivity extends Activity implements IDynamicViewAdd, ICustomViewAdd {

    private SkinLayoutInflaterFactory skinLayoutInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.register(this);

        skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(this);
        getLayoutInflater().setFactory(skinLayoutInflaterFactory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.unRegister(this);

        skinLayoutInflaterFactory.clear();
    }

    /**
     * 当前Activity换肤
     */
    void reSkin(SkinTheme theme) {
        skinLayoutInflaterFactory.reSkin(theme);
    }

    @Override
    final public void addSkinView(View view, List<DynamicViewAttribute> attrs) {
        skinLayoutInflaterFactory.addSkinViewIfNecessary(view, attrs);
    }

    @Override
    final public void addCustomView(CustomSkinView view) {
        skinLayoutInflaterFactory.addCustomView(view);
    }

    @Override
    final public void removeCustomView(CustomSkinView view) {
        skinLayoutInflaterFactory.removeCustomView(view);
    }
}
