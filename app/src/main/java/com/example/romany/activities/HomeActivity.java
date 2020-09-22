package com.example.romany.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.romany.DB.ChoirModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.SlidehomeAdapter;
import com.example.romany.model.PagerClass;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import maes.tech.intentanim.CustomIntent;

public class HomeActivity extends AppCompatActivity {
    Button praise,About_Dev,Add_Choir;
    private SharedPreferences sharedPreferences;
    private List<PagerClass>pagers;
    private ViewPager viewPager;
    private SlidehomeAdapter adapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);






        initiView();
        if (!Check())
        {
            SaveShared(true);
            AddChoirs();
        }



        move(praise,ShowTranemActivity.class);
        move(Add_Choir,AllChoirs.class);
        move(About_Dev,DeveloperActivity.class);

    }

    private void initiView() {
        praise=(Button)findViewById(R.id.praise_btn);

        Add_Choir=(Button)findViewById(R.id.Add_DB_btn);

        About_Dev=(Button)findViewById(R.id.about_dev_btn);
        viewPager=(ViewPager)findViewById(R.id.homePager);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);

        //shared pref
        sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        pagers=new ArrayList<>();

        //Slider
        pagers.add(new PagerClass(R.drawable.home));
        pagers.add(new PagerClass(R.drawable.saved));
        pagers.add(new PagerClass(R.drawable.catsaved));
        pagers.add(new PagerClass(R.drawable.homesaved));
        adapter=new SlidehomeAdapter(HomeActivity.this,pagers);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager,true);


        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(),4000,4000);
    }


    private void move(Button button, final Class c)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, c);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AddChoirs() {
        ChoirModel choirModel=new ChoirModel();
        choirModel.setChoirName("كورال ملايكه");
        RomanyDbOperation choir=new RomanyDbOperation();
        choir.insertAndUpdateChoir(choirModel);

        ChoirModel choirModel2=new ChoirModel();
        choirModel2.setChoirName("كورال تى بارثينوس");
        RomanyDbOperation choir2=new RomanyDbOperation();
        choir2.insertAndUpdateChoir(choirModel2);

        ChoirModel choirModel3=new ChoirModel();
        choirModel3.setChoirName("كورال مارمينا");
        RomanyDbOperation choir3=new RomanyDbOperation();
        choir3.insertAndUpdateChoir(choirModel3);

        ChoirModel choirModel4=new ChoirModel();
        choirModel4.setChoirName("كورال القديسين");
        RomanyDbOperation choir4=new RomanyDbOperation();
        choir4.insertAndUpdateChoir(choirModel4);

        ChoirModel choirModel5=new ChoirModel();
        choirModel5.setChoirName("كورال معلم الاجيال");
        RomanyDbOperation choir5=new RomanyDbOperation();
        choir5.insertAndUpdateChoir(choirModel5);

        ChoirModel choirModel6=new ChoirModel();
        choirModel6.setChoirName("كورال الانبا انطونيوس");
        RomanyDbOperation choir6=new RomanyDbOperation();
        choir6.insertAndUpdateChoir(choirModel6);

        ChoirModel choirModel7=new ChoirModel();
        choirModel7.setChoirName("كورال بركه");
        RomanyDbOperation choir7=new RomanyDbOperation();
        choir7.insertAndUpdateChoir(choirModel7);

        ChoirModel choirModel8=new ChoirModel();
        choirModel8.setChoirName("كورال ابونا اندراوس");
        RomanyDbOperation choir8=new RomanyDbOperation();
        choir8.insertAndUpdateChoir(choirModel8);

        ChoirModel choirModel9=new ChoirModel();
        choirModel9.setChoirName("كورال امنا العدرا");
        RomanyDbOperation choir9=new RomanyDbOperation();
        choir9.insertAndUpdateChoir(choirModel9);

        ChoirModel choirModel10=new ChoirModel();
        choirModel10.setChoirName("كورال مارافرام السريانى");
        RomanyDbOperation choir10=new RomanyDbOperation();
        choir10.insertAndUpdateChoir(choirModel10);
    }

    private void SaveShared(Boolean Saved)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("First",Saved);
        editor.apply();
    }

    private Boolean Check()
    {
        Boolean share=sharedPreferences.getBoolean("First",false);
        return share;
    }

    class SliderTimer extends TimerTask{
        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()<pagers.size()-1)
                    {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }
                    else
                        viewPager.setCurrentItem(0);
                }
            });
        }
    }
}
