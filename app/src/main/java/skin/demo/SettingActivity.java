package skin.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skin.lib.BaseSkinActivity;
import skin.lib.DynamicViewAttribute;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

public class SettingActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.change_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinTheme targetTheme = SkinManager.getTheme() == SkinTheme.DEFAULT ? SkinTheme.NIGHT
                        : SkinTheme.DEFAULT;
                SkinManager.reSkin(targetTheme);
                reSkin(targetTheme);
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root);
        TextView tv = new TextView(this);
        tv.setText("This is a TextView added in code");
        int colorResId = R.color.textColor;
        tv.setTextColor(getResources().getColor(colorResId));
        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.image));
        linearLayout.addView(tv);

        List<DynamicViewAttribute> attrs = new ArrayList<>();
        attrs.add(new DynamicViewAttribute("textColor", colorResId));
        attrs.add(new DynamicViewAttribute("background", R.drawable.image));
        addSkinView(tv, attrs);

        SkinnableView dCView = new SkinnableView(this);
        linearLayout.addView(dCView);
    }
}
