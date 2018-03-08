package skin.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import skin.lib.item.ImageViewSrcItem;
import skin.lib.item.ListViewDividerItem;
import skin.lib.item.ListViewListSelectorItem;
import skin.lib.item.TextViewCompoundDrawablesItem;
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
     * 换肤支持的属性名
     */
    private static final String ATTR_VIEW_BACKGROUND = "background";
    private static final String ATTR_TEXTVIEW_TEXTCOLOR = "textColor";
    private static final String ATTR_TEXTVIEW_COMPOUNDDRAWABLES = "compoundDrawables";
    private static final String ATTR_IMAGEVIEW_SRC = "src";
    private static final String ATTR_LISTVIEW_DIVIDER = "divider";
    private static final String ATTR_ABSLISTVIEW_LISTSELECTOR = "listSelector";

    /**
     * 换肤支持的属性id
     */
    private final static int[] ATTRS_VIEW = new int[]{
            android.R.attr.background
    };
    // Top/Bottom一定要放在数组中Left/Right前面,否则Top/Bottom的peekValue为null
    private final static int[] ATTRS_TEXTVIEW = new int[]{
            android.R.attr.textColor,
            android.R.attr.drawableTop,
            android.R.attr.drawableBottom,
            android.R.attr.drawableLeft,
            android.R.attr.drawableRight
    };
    private final static int[] ATTRS_IMAGEVIEW = new int[]{
            android.R.attr.src
    };
    private final static int[] ATTRS_LISTVIEW = new int[]{
            android.R.attr.divider
    };
    private final static int[] ATTRS_ABSLISTVIEW = new int[]{
            android.R.attr.listSelector
    };

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

    /**
     * 最小的自定义资源id,区别于系统资源id.大于0x70000000的是自定义资源
     */
    private static final int MIN_CUSTOM_RESOURCE_ID = 0x70000000;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    /**
     * 换肤支持的各类型item列表，创建View时添加到列表，换肤时遍历列表修改属性值
     */
    private List<ViewBackgroundItem> mViewBackgroundItems = new ArrayList<>();
    private List<TextViewTextColorItem> mTextViewTextColorItems = new ArrayList<>();
    private List<TextViewCompoundDrawablesItem> mTextViewCompoundDrawablesItems = new
            ArrayList<>();
    private List<ImageViewSrcItem> mImageViewSrcItems = new ArrayList<>();
    private List<ListViewDividerItem> mListDividerItems = new ArrayList<>();
    private List<ListViewListSelectorItem> mListSelectorItems = new ArrayList<>();


    /**
     * 自定义View列表
     */
    private List<WeakReference<ICustomSkinView>> mCustomSkinViews = new ArrayList<>();

    /**
     * 换肤Activity使用的构造方法
     */
    SkinLayoutInflaterFactory(BaseSkinActivity activity) {
        mContext = activity.getApplicationContext();
        mLayoutInflater = activity.getLayoutInflater();
    }

    /**
     * 换肤Fragment使用的构造方法
     */
    SkinLayoutInflaterFactory(BaseSkinFragment fragment, LayoutInflater layoutInflater) {
        mContext = fragment.getActivity().getApplicationContext();
        mLayoutInflater = layoutInflater;
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

        View view = createView(name, attrs);

        if (view != null) {
            if (view instanceof ViewStub) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ((ViewStub) view).setLayoutInflater(mLayoutInflater);
                }
            } else {
                addSkinViewIfNecessary(view, attrs);
            }
        } else {
            L.e(TAG, "Dangerous!!! You miss view " + name);
        }

        return view;
    }


    /**
     * 手动创建View
     * 只有返回的View不为空，才能保证记录到需要修改主题的View
     *
     * @param name  Tag name to be inflated.
     * @param attrs Inflation attributes as specified in XML file.
     * @return View Newly created view. Return null for the default behavior.
     */
    private View createView(String name, AttributeSet attrs) {
        View view = null;
        // from PhoneLayoutInflater
        for (String prefix : sClassPrefixList) {
            try {
                view = mLayoutInflater.createView(name, prefix, attrs);
                if (view != null) break;
            } catch (Exception e) {
            }
        }

        if (view == null) {
            try {
                view = mLayoutInflater.createView(name, null, attrs);
            } catch (Exception e) {
            }
        }

        return view;
    }

    /**
     * 记录需要修改主题的View及其属性,支持style属性
     *
     * @param view 布局xml中需要修改主题的View
     * @param set  需要修改主题的View的属性
     */
    private void addSkinViewIfNecessary(View view, AttributeSet set) {
        // View
        int[] viewResIds = getResIds(set, ATTRS_VIEW);
        for (int i = 0; i < viewResIds.length; i++) {
            if (viewResIds[i] > MIN_CUSTOM_RESOURCE_ID) {
                switch (i) {
                    case 0:
                        addSkinView(view, ATTR_VIEW_BACKGROUND, viewResIds[i]);
                        break;
                }
            }
        }

        // TextView
        if (view instanceof TextView) {
            int[] textViewResIds = getResIds(set, ATTRS_TEXTVIEW);
            int[] compoundDrawableResIds = new int[]{-1, -1, -1, -1};
            boolean compoundValid = false;
            for (int i = 0; i < textViewResIds.length; i++) {
                if (textViewResIds[i] > MIN_CUSTOM_RESOURCE_ID) {
                    switch (i) {
                        case 0:
                            addSkinView(view, ATTR_TEXTVIEW_TEXTCOLOR, textViewResIds[i]);
                            break;
                        case 1:
                            compoundDrawableResIds[0] = textViewResIds[i];
                            compoundValid = true;
                            break;
                        case 2:
                            compoundDrawableResIds[1] = textViewResIds[i];
                            compoundValid = true;
                            break;
                        case 3:
                            compoundDrawableResIds[2] = textViewResIds[i];
                            compoundValid = true;
                            break;
                        case 4:
                            compoundDrawableResIds[3] = textViewResIds[i];
                            compoundValid = true;
                            break;
                    }
                }
            }
            if (compoundValid) {
                addSkinView(view, ATTR_TEXTVIEW_COMPOUNDDRAWABLES, compoundDrawableResIds);
            }

        }

        // ImageView
        if (view instanceof ImageView) {
            int[] imageViewResIds = getResIds(set, ATTRS_IMAGEVIEW);
            for (int i = 0; i < imageViewResIds.length; i++) {
                if (imageViewResIds[i] > MIN_CUSTOM_RESOURCE_ID) {
                    switch (i) {
                        case 0:
                            addSkinView(view, ATTR_IMAGEVIEW_SRC, imageViewResIds[i]);
                            break;
                    }
                }
            }
        }

        // AbsListView
        if (view instanceof AbsListView) {
            int[] absListViewResIds = getResIds(set, ATTRS_ABSLISTVIEW);
            for (int i = 0; i < absListViewResIds.length; i++) {
                if (absListViewResIds[i] > MIN_CUSTOM_RESOURCE_ID) {
                    switch (i) {
                        case 0:
                            addSkinView(view, ATTR_ABSLISTVIEW_LISTSELECTOR, absListViewResIds[i]);
                            break;
                    }
                }
            }

            // ListView
            if (view instanceof ListView) {
                int[] listViewResIds = getResIds(set, ATTRS_LISTVIEW);
                for (int i = 0; i < listViewResIds.length; i++) {
                    if (listViewResIds[i] > MIN_CUSTOM_RESOURCE_ID) {
                        switch (i) {
                            case 0:
                                addSkinView(view, ATTR_LISTVIEW_DIVIDER, listViewResIds[i]);
                                break;
                        }
                    }
                }
            }
        }

    }

    /**
     * 注意该方法可能获取的资源id与xml布局中申明的不一致.
     * 不一致的情况:xml布局中申明的资源是一个临时资源,如:
     * <color name="temp">@color/red</color>
     * <color name="red">#FF0000</color>
     * 获取的id为R.color.red的id.
     */
    private int[] getResIds(AttributeSet set, int[] attrs) {
        int[] resIds = new int[attrs.length];
        for (int i = 0; i < resIds.length; i++) {
            resIds[i] = -1;
        }

        TypedArray typedArray = mContext.obtainStyledAttributes(set, attrs);
        TypedValue value;
        for (int i = 0; i < resIds.length; i++) {
            value = typedArray.peekValue(i);
            if (value != null) {
                resIds[i] = value.resourceId;
            }
        }
        typedArray.recycle();

        return resIds;
    }

    /**
     * 记录需要修改主题的动态添加View及其属性
     *
     * @param view  动态添加的需要修改主题的View
     * @param attrs 需要修改主题的View的属性
     */
    void addSkinViewIfNecessary(View view, List<DynamicViewAttribute> attrs) {
        for (DynamicViewAttribute attr : attrs) {
            addSkinView(view, attr.mAttrName, attr.mResId);
        }
    }

    /**
     * 添加View到换肤管理列表
     *
     * @param view     view
     * @param attrName 换肤属性
     * @param resId    默认资源id
     */
    private void addSkinView(View view, String attrName, int resId) {
        switch (attrName) {
            case ATTR_VIEW_BACKGROUND:
                String typeName = mContext.getResources().getResourceTypeName(resId);
                mViewBackgroundItems.add(new ViewBackgroundItem(view, resId, typeName));
                break;
            case ATTR_TEXTVIEW_TEXTCOLOR:
                mTextViewTextColorItems.add(new TextViewTextColorItem((TextView) view, resId));
                break;
            case ATTR_IMAGEVIEW_SRC:
                mImageViewSrcItems.add(new ImageViewSrcItem((ImageView) view, resId));
                break;
            case ATTR_LISTVIEW_DIVIDER:
                mListDividerItems.add(new ListViewDividerItem((ListView) view, resId));
                break;
            case ATTR_ABSLISTVIEW_LISTSELECTOR:
                mListSelectorItems.add(new ListViewListSelectorItem((AbsListView) view, resId));
                break;
        }
    }

    /**
     * 添加View到换肤管理列表(多个资源id)
     *
     * @param view     view
     * @param attrName 换肤属性
     * @param resIds   默认资源id
     */
    private void addSkinView(View view, String attrName, int[] resIds) {
        switch (attrName) {
            case ATTR_TEXTVIEW_COMPOUNDDRAWABLES:
                mTextViewCompoundDrawablesItems.add(new TextViewCompoundDrawablesItem(
                        (TextView) view, resIds[2], resIds[0], resIds[3], resIds[1]));
                break;
        }
    }

    /**
     * 记录需要修改主题的View及其属性,添加时调用一次reSkin()以实现初始化
     *
     * @param view 自定义的View
     */
    void addCustomView(ICustomSkinView view) {
        view.reSkin(SkinManager.getTheme());
        mCustomSkinViews.add(new WeakReference<>(view));
    }

    /**
     * 移除换肤列表中自定义View
     *
     * @param view 自定义View
     */
    void removeCustomView(ICustomSkinView view) {
        for (WeakReference<ICustomSkinView> ref : mCustomSkinViews) {
            ICustomSkinView refView = ref.get();
            if (refView != null && refView == view) {
                mCustomSkinViews.remove(ref);
                break;
            }
        }
    }

    /**
     * 修改主题
     */
    void reSkin(SkinTheme theme) {
        for (ViewBackgroundItem item : mViewBackgroundItems) {
            item.reSkinIfNecessary(theme);
        }
        for (TextViewTextColorItem item : mTextViewTextColorItems) {
            item.reSkinIfNecessary(theme);
        }
        for (TextViewCompoundDrawablesItem item : mTextViewCompoundDrawablesItems) {
            item.reSkinIfNecessary(theme);
        }
        for (ImageViewSrcItem item : mImageViewSrcItems) {
            item.reSkinIfNecessary(theme);
        }
        for (ListViewDividerItem item : mListDividerItems) {
            item.reSkinIfNecessary(theme);
        }
        for (ListViewListSelectorItem item : mListSelectorItems) {
            item.reSkinIfNecessary(theme);
        }

        for (WeakReference<ICustomSkinView> ref : mCustomSkinViews) {
            ICustomSkinView view = ref.get();
            if (view != null) {
                view.reSkin(theme);
            }
        }
    }

    /**
     * 清空记录换肤item的列表
     */
    void clear() {
        mViewBackgroundItems.clear();
        mTextViewTextColorItems.clear();
        mTextViewCompoundDrawablesItems.clear();
        mImageViewSrcItems.clear();
        mListDividerItems.clear();
        mListSelectorItems.clear();

        mCustomSkinViews.clear();
    }
}