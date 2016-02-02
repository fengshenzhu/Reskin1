package skin.theory;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现LayoutInflater.Factory，通过实现其onCreateView()方法，手动创建好布局文件中的View。
 * 遍历自行实例化的View可记录需要修改主题的View及其属性，修改这些View的属性值即可实现换肤。
 * <p/>
 * Created by fengshzh on 1/20/16.
 */
public class SkinLayoutInflaterFactory implements LayoutInflater.Factory {
    private static final String TAG = "SkinLayoutInflaterFactory";

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

    Context mContext;
    LayoutInflater mLayoutInflater;

    List<TextViewTextColorItem> textViewTextColorList = new ArrayList<>();

    public SkinLayoutInflaterFactory(Activity activity) {
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
        try {
            View view = createView(name, attrs);
            if (view != null) {
                addSkinViewIfNecessary(view, attrs);
                return view;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "Dangerous!!! You may miss view " + name);
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
                    Log.d(TAG, "Inflate view success: " + name + ", " + attrs);
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
     * @param view  需要修改主题的View
     * @param attrs 需要修改主题的View的属性
     */
    private void addSkinViewIfNecessary(View view, AttributeSet attrs) {
        if (view instanceof TextView) {
            int n = attrs.getAttributeCount();
            for (int i = 0; i < n; i++) {
                String attrName = attrs.getAttributeName(i);

                if (attrName.equals("textColor")) {
                    int id = 0;
                    String attrValue = attrs.getAttributeValue(i);
                    if (attrValue.startsWith("@")) {
                        id = Integer.parseInt(attrValue.substring(1));
                        textViewTextColorList.add(new TextViewTextColorItem((TextView) view, id));
                    }
                }
            }
        }
    }

    /**
     * 修改主题
     *
     * @param suffix 新主题资源后缀名
     */
    public void reSkin(String suffix) {
        for (TextViewTextColorItem skinItem : textViewTextColorList) {
                skinItem.view.setTextColor(getColor(skinItem.id, suffix));
        }
    }

    /**
     * 获取新主题color值
     *
     * @param resId  资源id
     * @param suffix 新主题资源后缀名
     * @return 新主题color值
     */
    private int getColor(int resId, String suffix) {
        String resName = mContext.getResources().getResourceEntryName(resId);
        String newResName = resName + suffix;
        int newResId = mContext.getResources().getIdentifier(newResName, "color", mContext
                .getPackageName());
        return mContext.getResources().getColor(newResId);
    }
}
