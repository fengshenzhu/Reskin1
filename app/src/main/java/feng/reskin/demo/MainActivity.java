package feng.reskin.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import skin.lib.BaseSkinActivity;
import skin.lib.SkinManager;
import skin.lib.SkinTheme;

public class MainActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.systemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReskinSystemViewActivity.class));
            }
        });

        findViewById(R.id.customView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReskinCustomViewActivity.class));
            }
        });

        findViewById(R.id.fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReskinFragmentActivity.class));
            }
        });

        findViewById(R.id.reskin_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.DEFAULT, MainActivity.this);
            }
        });
        findViewById(R.id.reskin_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.WHITE, MainActivity.this);
            }
        });
        findViewById(R.id.reskin_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.reSkin(SkinTheme.BLACK, MainActivity.this);
            }
        });
    }
}
