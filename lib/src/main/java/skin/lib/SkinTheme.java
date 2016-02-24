package skin.lib;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 皮肤主题，支持的皮肤主题和资源后缀在此设置
 * <p/>
 * Created by fengshzh on 1/21/16.
 */
public enum SkinTheme {
    /**
     * 默认主题
     */
    DEFAULT("") {
        @Override
        int getId(int resId, String defType) {
            return resId;
        }
    },

    /**
     * 夜间主题
     */
    NIGHT("_night") {
        @Override
        int getId(int resId, String defType) {
            int newResId = resId;
            try {
                newResId = getNewResId(resId, getSuffix(), defType);
            } catch (Resources.NotFoundException e) {
            }
            return newResId;
        }
    };


    /**
     * 主题资源后缀名
     */
    private String suffix;

    SkinTheme(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 获取主题下后缀
     *
     * @return 后缀
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 获取主题下新资源id
     *
     * @param resId   默认主题资源id
     * @param defType 资源类型
     * @return 主题下资源id, 找不到资源时返回默认主题资源id
     */
    abstract int getId(int resId, String defType);

    /**
     * 获取主题下资源id
     *
     * @param resId 默认主题资源id
     * @return 主题下资源id, 找不到资源时返回默认主题资源id
     */
    public int getId(int resId) {
        return getId(resId, SkinManager.getContext().getResources().getResourceTypeName(resId));
    }

    /**
     * 获取主题下color值
     *
     * @param resId 默认主题资源id
     * @return 主题下color值
     * @throws Resources.NotFoundException Throws NotFoundException if new resource id invalid.
     */
    public int getColor(int resId) throws Resources.NotFoundException {
        int newResId = getId(resId, "color");
        return SkinManager.getContext().getResources().getColor(newResId);
    }

    /**
     * 获取主题下drawable值
     *
     * @param resId 默认主题资源id
     * @return 主题下drawable值
     * @throws Resources.NotFoundException Throws NotFoundException if new resource id invalid.
     */
    public Drawable getDrawable(int resId) throws Resources.NotFoundException {
        int newResId = getId(resId, "drawable");
        return SkinManager.getContext().getResources().getDrawable(newResId);
    }

    /**
     * 根据后缀获取新资源id
     *
     * @param suffix  资源命名添加的后缀
     * @param defType 资源类型
     * @return 新资源id
     * @throws Resources.NotFoundException Throws NotFoundException if the given ID invalid.
     */
    private static int getNewResId(int resId, String suffix, String defType) throws Resources
            .NotFoundException {
        String resName = SkinManager.getContext().getResources().getResourceEntryName(resId);
        String newResName = resName + suffix;
        int newResId = SkinManager.getContext().getResources().getIdentifier(newResName, defType,
                SkinManager.getContext().getPackageName());
        if (newResId == 0) {
            throw new Resources.NotFoundException();
        } else {
            return newResId;
        }
    }
}

