package com.example.romany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.romany.R;

public class Splash extends AppCompatActivity {
    public static final int SPLASH_SCREEN=4000;
    Animation top_anim,bottom_anim;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Difine Animations
        top_anim= AnimationUtils.loadAnimation(Splash.this,R.anim.top_anim);
        bottom_anim= AnimationUtils.loadAnimation(Splash.this,R.anim.bottom_anim);

        //Difine View
        textView=(TextView)findViewById(R.id.tittle_praise);
        imageView=(ImageView)findViewById(R.id.imageView);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            imageView.setAnimation(top_anim);
            textView.setAnimation(bottom_anim);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
