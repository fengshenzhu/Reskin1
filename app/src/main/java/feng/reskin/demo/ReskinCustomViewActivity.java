package feng.reskin.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 演示自定义View
 * Created by fengshzh on 16/2/29.
 */
public class ReskinCustomViewActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        findViewById(R.id.reskin_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.DEFAULT, ReskinCustomViewActivity.this);
            }
        });
        findViewById(R.id.reskin_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.WHITE, ReskinCustomViewActivity.this);
            }
        });
        findViewById(R.id.reskin_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.BLACK, ReskinCustomViewActivity.this);
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        /** 手动添加自行实现了onDraw()的自定义View */
        CustomView customView = new CustomView(this);
        customView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, 200));
        linearLayout.addView(customView);


        /** 手动添加未自行实现onDraw()的自定义View */
        CustomTextView customLinearLayout = new CustomTextView(this);
        customLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, 200));
        linearLayout.addView(customLinearLayout);
    }
}
