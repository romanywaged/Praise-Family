package com.example.romany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.romany.R;
import com.example.romany.fragments.AddChildFragment;
import com.example.romany.fragments.HomeFragment;
import com.example.romany.fragments.ViewAllFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChildDBActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView navigationView;
    int Choir_ID;
    String Choir_Name;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_db);
        navigationView=findViewById(R.id.bottom_Nav);
        pager=findViewById(R.id.viewPager);
        Choir_ID =getIntent().getExtras().getInt("ChoirID");
        Choir_Name =getIntent().getExtras().getString("ChoirName");
        HomeFragment homeFragment=new HomeFragment();
        replaceFagment("Home",homeFragment, false);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        int fragmentSize = getSupportFragmentManager().getBackStackEntryCount();

        if(fragmentSize > 1)
            getSupportFragmentManager().popBackStack();

         else
            finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.home_menu:
                HomeFragment homeFragment=new HomeFragment();
                replaceFagment("Home",homeFragment, false);
                return true;
            case R.id.add_child_menu:
                AddChildFragment addChildFragment=new AddChildFragment();
                replaceFagment("Add Child",addChildFragment, false);
                return true;
            case R.id.view_provs:
                ViewAllFragment viewAllFragment=new ViewAllFragment();
                replaceFagment("All Children",viewAllFragment, false);
                return true;
        }
        return false;
    }



    private void replaceFagment(String tittle, Fragment fragment, boolean isAddToBackStack)
    {
        FragmentManager manager2=getSupportFragmentManager();
        FragmentTransaction transaction2=manager2.beginTransaction();
        Bundle bundle2=new Bundle();
        bundle2.putInt("id",Choir_ID);
        fragment.setArguments(bundle2);

        if(isAddToBackStack)
            transaction2.replace(R.id.contain,fragment).addToBackStack(tittle).commit();

        else
            transaction2.replace(R.id.contain,fragment).commit();

        getSupportActionBar().setTitle(tittle);
    }
}
