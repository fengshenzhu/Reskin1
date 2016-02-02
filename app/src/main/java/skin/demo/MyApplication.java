package skin.demo;

import android.app.Application;

import skin.lib.SkinManager;

/**
 * Created by fengshzh on 1/26/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.init(this);
    }
}
