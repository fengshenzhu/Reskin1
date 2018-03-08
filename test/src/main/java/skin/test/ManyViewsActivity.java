package skin.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

public class ManyViewsActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_views);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
