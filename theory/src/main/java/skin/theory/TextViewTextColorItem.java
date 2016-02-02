package skin.theory;

import android.widget.TextView;

/**
 * 需要换肤的单元，记录View及其属性
 * <p/>
 * Created by fengshzh on 1/20/16.
 */
public class TextViewTextColorItem {
    TextView view;
    int id;

    public TextViewTextColorItem(TextView view, int id) {
        this.view = view;
        this.id = id;
    }
}
