package com.paprbit.bhamashah.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paprbit.bhamashah.R;

public class Splash extends AppCompatActivity {
    Handler handler;
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,HomeActivity.class));
                Splash.this.finish();
            }
        };

        handler.postDelayed(r,1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
    }
}
