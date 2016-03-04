package skin.test;

import android.app.Application;

import skin.lib.SkinManager;

/**
 * Created by fengshzh on 16/3/2.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.init(this);
    }
}
