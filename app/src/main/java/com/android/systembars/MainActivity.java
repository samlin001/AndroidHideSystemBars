package com.android.systembars;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    TextView mTextView1, mTextView2;
    WindowInsetsControllerCompat mWindowInsetsController;
    Boolean config_remoteInsetsControllerControlsSystemBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);

        // Get android.R.bool.config_remoteInsetsControllerControlsSystemBars by its id
        // https://cs.android.com/android/platform/superproject/main/+/main:frameworks/base/core/res/res/values/public-final.xml?q=0x01110006
        final int config_remoteInsetsControllerControlsSystemBarsId = 0x01110006;
        config_remoteInsetsControllerControlsSystemBars = getResources().getBoolean(config_remoteInsetsControllerControlsSystemBarsId);
        mTextView1.setText("config_remoteInsetsControllerControlsSystemBars: " +
                config_remoteInsetsControllerControlsSystemBars);

        mWindowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        getWindow().getDecorView().setOnApplyWindowInsetsListener((view, windowInsets) -> {
            StringBuffer sb = new StringBuffer("OnApplyWindowInsetsListener");
            sb.append("\nnavigationBars: ");
            sb.append(windowInsets.isVisible(WindowInsetsCompat.Type.statusBars()) ?
                    "visible" : "invisible");
            sb.append("\nstatusBars: ");
            sb.append(windowInsets.isVisible(WindowInsetsCompat.Type.statusBars()) ?
                    "visible" : "invisible");
            mTextView2.setText(sb.toString());
            return view.onApplyWindowInsets(windowInsets);
        });
    }

    public void onShowSystemBars(View view) {
        mWindowInsetsController.show(WindowInsetsCompat.Type.systemBars());
    }

    public void onHideSystemBars(View view) {
        mWindowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        mWindowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}