package com.developerrr.tippytoegame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.developerrr.tippytoegame.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button trueFalseBtn,finishLiricsBtn,whoSaidItBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        trueFalseBtn=findViewById(R.id.trueOrfalse);
        finishLiricsBtn=findViewById(R.id.finishlyrics);
        whoSaidItBtn=findViewById(R.id.whosaidit);

        trueFalseBtn.setOnClickListener(this);
        finishLiricsBtn.setOnClickListener(this);
        whoSaidItBtn.setOnClickListener(this);

        transparentStatusAndNavigation();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.trueOrfalse:
                startActivity(new Intent(MainActivity.this,TrueFalseActivity.class));
                break;
            case R.id.finishlyrics:
                startActivity(new Intent(MainActivity.this,FinishLyricsActivity.class));
                break;
            case R.id.whosaidit:
                startActivity(new Intent(MainActivity.this,WhoSaidItActivity.class));
                break;
        }
    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}