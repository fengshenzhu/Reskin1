package feng.reskin.demo;

import android.app.Application;

import skin.lib.SkinManager;

/**
 * Created by fengshzh on 16/2/29.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**  初始化换肤库 */
        SkinManager.init(this);
    }
}
