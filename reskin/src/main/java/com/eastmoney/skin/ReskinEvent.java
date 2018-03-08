package com.eastmoney.skin;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinTheme;

/**
 * 换肤事件,{@link skin.lib.SkinManager#reSkin(SkinTheme, BaseSkinActivity)}换肤后,用EventBus post出全局事件.
 * 需要监听该事件的对象需要向EventBus进行注册/反注册,并实现onEvent(ReskinEvent)方法.
 * 换肤主题可通过{@link #getTheme()}获取.
 * <p/>
 * Created by fengshzh on 16/2/25.
 */
public class ReskinEvent {
    private SkinTheme mTheme;

    public ReskinEvent(SkinTheme theme) {
        mTheme = theme;
    }

    public SkinTheme getTheme() {
        return mTheme;
    }
}
