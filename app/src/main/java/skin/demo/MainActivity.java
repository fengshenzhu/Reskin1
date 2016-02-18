package skin.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skin.lib.BaseSkinActivity;
import skin.lib.DynamicViewAttribute;

public class MainActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.change_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        /** 动态添加系统View */
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root);
        TextView tv = new TextView(this);
        tv.setText("This is a TextView added in code");
        int colorResId = R.color.textColor;
        tv.setTextColor(getResources().getColor(colorResId));
        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.image));
        linearLayout.addView(tv);

        /** 动态添加系统View不走onCreateView,需要手动添加换肤关注属性 */
        List<DynamicViewAttribute> attrs = new ArrayList<>();
        attrs.add(new DynamicViewAttribute("textColor", colorResId));
        attrs.add(new DynamicViewAttribute("background", R.drawable.image));
        addSkinView(tv, attrs);

        /** 动态添加自定义View,换肤由自定义View自实现,Activity不需额外管理 */
        SkinnableView dCView = new SkinnableView(this);
        linearLayout.addView(dCView);
    }
}
