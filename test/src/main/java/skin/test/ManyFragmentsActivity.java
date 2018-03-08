package skin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

public class ManyFragmentsActivity extends BaseSkinActivity {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_fragments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        final int size = 100;
        final int store = 5;

        mViewPager.setOffscreenPageLimit(store);

        final Fragment[] fragments = new Fragment[size];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = new ManyFragment();
        }

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return size;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_theme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theme_default:
                SkinManager.reSkin(SkinTheme.DEFAULT, this);
                break;
            case R.id.theme_white:
                SkinManager.reSkin(SkinTheme.WHITE, this);
                break;
            case R.id.theme_black:
                SkinManager.reSkin(SkinTheme.BLACK, this);
                break;
            case R.id.custom_use:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                return false;
        }
        return true;
    }

}
