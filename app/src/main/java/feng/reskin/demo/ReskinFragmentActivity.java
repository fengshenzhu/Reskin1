package feng.reskin.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

/**
 * 放演示Fragment的Activity
 * Created by fengshzh on 16/2/29.
 */
public class ReskinFragmentActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        findViewById(R.id.reskin_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.DEFAULT, ReskinFragmentActivity.this);
                Toast.makeText(ReskinFragmentActivity.this, "触发换肤的Activity里Fragment系统View不生效", Toast
                        .LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.reskin_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.WHITE, ReskinFragmentActivity.this);
                Toast.makeText(ReskinFragmentActivity.this, "触发换肤的Activity里Fragment系统View不生效", Toast
                        .LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.reskin_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.BLACK, ReskinFragmentActivity.this);
                Toast.makeText(ReskinFragmentActivity.this, "触发换肤的Activity里Fragment系统View不生效", Toast
                        .LENGTH_SHORT).show();
            }
        });

        int size = 5;
        final Fragment[] fragments = new Fragment[size];
        for (int i = 0; i < size; i++) {
            fragments[i] = new ReskinFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragments[i].setArguments(bundle);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
    }
}
