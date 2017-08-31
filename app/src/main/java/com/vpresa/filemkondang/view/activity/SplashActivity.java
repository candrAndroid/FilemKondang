package com.vpresa.filemkondang.view.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.utility.AppConstant;
import com.vpresa.filemkondang.utility.CommonFunction;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CommonFunction.moveActivity(SplashActivity.this, MainActivity.class, true);
            }
        };

        handler.postDelayed(runnable, AppConstant.SPLASH_TIME);
    }
}
