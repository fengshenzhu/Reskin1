package skin.theory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    SkinLayoutInflaterFactory skinLayoutInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(this);
        getLayoutInflater().setFactory(skinLayoutInflaterFactory);

        setContentView(R.layout.activity_main);

        findViewById(R.id.change_text_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSkin();
            }
        });

    }

    private void reSkin() {
        skinLayoutInflaterFactory.reSkin("_night");
    }
}
