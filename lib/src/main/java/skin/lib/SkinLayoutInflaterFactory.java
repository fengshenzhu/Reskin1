package skin.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import skin.lib.item.ImageViewSrcItem;
import skin.lib.item.TextViewTextColorItem;
import skin.lib.item.ViewBackgroundItem;

/**
 * 实现换肤的核心类
 * 实现LayoutInflater.Factory，通过实现其onCreateView()方法，手动创建好布局文件中的View。
 * 遍历自行实例化的View可记录需要修改主题的View及其属性，修改这些View的属性值即可实现换肤。
 * <p/>
 * Created by fengshzh on 1/20/16.
 */
class SkinLayoutInflaterFactory implements LayoutInflater.Factory {
    private static final String TAG = "SkinLayoutInflaterFactory";

    /**
     * 换肤支持的属性
     */
    private static final String ATTR_TEXT_COLOR = "textColor";
    private static final String ATTR_SRC = "src";
    private static final String ATTR_BACKGROUND = "background";

    /**
     * 换肤支持的资源类型
     */
    private static final String RES_DRAWABLE = "drawable";
    private static final String RES_COLOR = "color";

    /**
     * 这几个前缀在xml布局文件中申明View时可省略，但是实例化View要使用Java反射机制调用其构造函数，需要补全类名
     * 前三个来自PhoneLayoutInflater，第四个来自LayoutInflater
     */
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    /**
     * 换肤支持的各类型item列表，创建View时添加到列表，换肤时遍历列表修改属性值
     */
    private List<ViewBackgroundItem> mViewBackgroundItems = new ArrayList<>();
    private List<TextViewTextColorItem> mTextViewTextColorItems = new ArrayList<>();
    private List<ImageViewSrcItem> mImageViewSrcItems = new ArrayList<>();

    /**
     * 自定义View列表
     */
    private List<WeakReference<ICustomSkinView>> mCustomSkinViews = new ArrayList<>();

    SkinLayoutInflaterFactory(Activity activity) {
        mContext = activity;
        mLayoutInflater = activity.getLayoutInflater();
    }

    /**
     * Hook you can supply that is called when inflating from a LayoutInflater.
     * You can use this to customize the tag names available in your XML
     * layout files.
     * <p/>
     * <p/>
     * Note that it is good practice to prefix these custom names with your
     * package (i.e., com.coolcompany.apps) to avoid conflicts with system
     * names.
     *
     * @param name    Tag name to be inflated.
     * @param context The context the view is being created in.
     * @param attrs   Inflation attributes as specified in XML file.
     * @return View Newly created view. Return null for the default
     * behavior.
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // fragment交由系统处理,调用到Fragment.onCreateView()
        if ("fragment".equals(name)) {
            return null;
        }

        try {
            View view = createView(name, attrs);
            if (view != null) {
                addSkinViewIfNecessary(view, attrs);
                return view;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        L.e(TAG, "Dangerous!!! You miss view " + name);
        return null;
    }


    /**
     * 手动创建View
     * 只有返回的View不为空，才能保证记录到需要修改主题的View
     *
     * @param name  Tag name to be inflated.
     * @param attrs Inflation attributes as specified in XML file.
     * @return View Newly created view. Return null for the default behavior.
     * @throws ClassNotFoundException
     */
    private View createView(String name, AttributeSet attrs) throws ClassNotFoundException {
        // from PhoneLayoutInflater
        for (String prefix : sClassPrefixList) {
            try {
                View view = mLayoutInflater.createView(name, prefix, attrs);
                if (view != null) {
                    L.d(TAG, "Inflate view success: " + name + ", " + attrs);
                    return view;
                }
            } catch (ClassNotFoundException e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }

        return mLayoutInflater.createView(name, null, attrs);
    }

    /**
     * 记录需要修改主题的View及其属性
     *
     * @param view  布局xml中需要修改主题的View
     * @param attrs 需要修改主题的View的属性
     */
    private void addSkinViewIfNecessary(View view, AttributeSet attrs) {
        // TODO 按View Type遍历还是按attrs遍历，效率待比较
        // 按attrs遍历
        int n = attrs.getAttributeCount();
        for (int i = 0; i < n; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (attrValue.startsWith("@")) {
                int resId = Integer.parseInt(attrValue.substring(1));
                addSkinViewIfNecessary(view, attrName, resId);
            }
        }
    }

    /**
     * 记录需要修改主题的View及其属性
     *
     * @param view  动态添加的需要修改主题的View
     * @param attrs 需要修改主题的View的属性
     */
    void addSkinViewIfNecessary(View view, List<DynamicViewAttribute> attrs) {
        for (DynamicViewAttribute attr : attrs) {
            addSkinViewIfNecessary(view, attr.attrName, attr.resId);
        }
    }

    /**
     * 添加View到换肤管理列表
     *
     * @param view     view
     * @param attrName 换肤属性
     * @param resId    默认资源id
     */
    private void addSkinViewIfNecessary(View view, String attrName, int resId) {
        switch (attrName) {
            case ATTR_BACKGROUND:
                String typeName = mContext.getResources().getResourceTypeName(resId);
                mViewBackgroundItems.add(new ViewBackgroundItem(view, resId, typeName));
                break;
            case ATTR_TEXT_COLOR:
                mTextViewTextColorItems.add(new TextViewTextColorItem((TextView) view, resId));
                break;
            case ATTR_SRC:
                mImageViewSrcItems.add(new ImageViewSrcItem((ImageView) view, resId));
                break;
        }
    }

    /**
     * 记录需要修改主题的View及其属性
     *
     * @param view 自定义的View
     */
    void addCustomView(ICustomSkinView view) {
        mCustomSkinViews.add(new WeakReference<>(view));
    }

    /**
     * 移除换肤列表中自定义View
     *
     * @param view 自定义View
     */
    void removeCustomView(ICustomSkinView view) {
        for (WeakReference<ICustomSkinView> ref : mCustomSkinViews) {
            if (ref.get() != null && ref.get() == view) {
                mCustomSkinViews.remove(ref);
                return;
            }
        }
    }

    /**
     * 修改主题
     */
    void reSkin(SkinTheme theme) {
        for (TextViewTextColorItem item : mTextViewTextColorItems) {
            TextView textView = item.view.get();
            if (textView != null) {
                try {
                    textView.setTextColor(theme.getColor(item.resId));
                } catch (Resources.NotFoundException e) {
                    // 找不到主题资源不改变属性值，异常不处理。以下异常捕捉处同。
                }
            }
        }

        for (ImageViewSrcItem item : mImageViewSrcItems) {
            ImageView imageView = item.view.get();
            if (imageView != null) {
                try {
                    imageView.setImageDrawable(theme.getDrawable(item.resId));
                } catch (Resources.NotFoundException e) {
                }
            }
        }

        for (ViewBackgroundItem item : mViewBackgroundItems) {
            View view = item.view.get();
            if (view != null) {
                if (item.typeName.equals(RES_COLOR)) {
                    try {
                        view.setBackgroundColor(theme.getColor(item.resId));
                    } catch (Resources.NotFoundException e) {
                    }
                } else if (item.typeName.equals(RES_DRAWABLE)) {
                    try {
                        view.setBackgroundDrawable(theme.getDrawable(item.resId));
                    } catch (Resources.NotFoundException e) {
                    }
                }
            }
        }

        for (WeakReference<ICustomSkinView> ref : mCustomSkinViews) {
            if (ref.get() != null) {
                ref.get().reSkin(theme);
            }
        }

    }

    /**
     * 清空记录换肤item的列表
     */
    void clear() {
        mViewBackgroundItems.clear();
        mTextViewTextColorItems.clear();
        mImageViewSrcItems.clear();

        mCustomSkinViews.clear();
    }
}
