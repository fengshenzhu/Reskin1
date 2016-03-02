package feng.reskin.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skin.lib.BaseSkinActivity;
import skin.lib.DynamicViewAttribute;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 演示系统View
 * Created by fengshzh on 16/2/29.
 */
public class ReskinSystemViewActivity extends BaseSkinActivity {
    ViewStub viewStub;
    boolean isInflated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_view);

        findViewById(R.id.reskin_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.DEFAULT, ReskinSystemViewActivity.this);
            }
        });
        findViewById(R.id.reskin_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.WHITE, ReskinSystemViewActivity.this);
            }
        });
        findViewById(R.id.reskin_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.BLACK, ReskinSystemViewActivity.this);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.listview_item, new String[]{"list " +
                "item 1", "list item 2", "list item 3"}));

        viewStub = (ViewStub) findViewById(R.id.stub);
        findViewById(R.id.inflateViewStub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInflated) {
                    viewStub.inflate();
                    isInflated = true;
                }
            }
        });

        /** 动态添加系统View */
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView textView = new TextView(this);
        textView.setText("手动add TextView textColor @color");
        int colorResId = R.color.bg_color;
        textView.setTextColor(getResources().getColor(colorResId));
        linearLayout.addView(textView);

        /** 动态添加系统View不走onCreateView,需要手动添加换肤关注属性 */
        List<DynamicViewAttribute> attrs = new ArrayList<>();
        attrs.add(new DynamicViewAttribute("textColor", colorResId));
        addSkinView(textView, attrs);
    }
}
