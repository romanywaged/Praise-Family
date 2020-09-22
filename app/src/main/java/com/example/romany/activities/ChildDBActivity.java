package com.example.romany.activities;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.romany.R;
import com.example.romany.base.BaseActivity;
import com.example.romany.fragments.AddChildFragment;
import com.example.romany.fragments.HomeFragment;
import com.example.romany.fragments.ViewAllFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;


public class ChildDBActivity extends BaseActivity
{
    BottomNavigationView navigationView;
    ViewPager pager;
    HomeFragment homeFragment;

    private int Choir_ID = 0;
    String Choir_Name = "";

    @Override
    protected int getFragmentPlaceHolder()
    {
        return R.id.contain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_db);

        navigationView=findViewById(R.id.coir_bottom_Nav);
        pager=findViewById(R.id.viewPager);

        if(getIntent() != null)
        {
            Choir_ID = Objects.requireNonNull(getIntent().getExtras()).getInt("ChoirID");
            Choir_Name = getIntent().getExtras().getString("ChoirName");
        }

        homeFragment = HomeFragment.newInstance(Choir_ID);
        replaceFragment(homeFragment, homeFragment.getTag(), false);

        navigateToActiveFragment();
    }

    private void navigateToActiveFragment()
    {
        navigationView.setOnNavigationItemSelectedListener(item ->
        {
            String fragmentTitle = "";
            boolean isMainTab = false;

            switch (item.getItemId())
            {
                case R.id.home_menu:
                    homeFragment = HomeFragment.newInstance(Choir_ID);
                    replaceFragment(homeFragment, homeFragment.getTag(), false);
                    fragmentTitle = "Home";
                    isMainTab = true;
                    break;

                case R.id.add_child_menu:
                    AddChildFragment addChildFragment = AddChildFragment.newInstance(Choir_ID);
                    replaceFragment(addChildFragment,addChildFragment.getTag(), false);
                    fragmentTitle = "Add Child";
                    isMainTab = true;
                    break;

                case R.id.view_provs:
                    ViewAllFragment viewAllFragment = ViewAllFragment.newInstance(Choir_ID);
                    replaceFragment(viewAllFragment ,viewAllFragment.getTag(), false);
                    fragmentTitle = "All Children";
                    isMainTab = true;
                    break;
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle(fragmentTitle);
            return isMainTab;
        });
    }

    @Override
    public void onBackPressed()
    {
        //If -----> current fragment is one of our parent 3 fragment close this activity
        //Else -----> only close the current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contain);
        if(currentFragment instanceof HomeFragment || currentFragment instanceof ViewAllFragment
                || currentFragment instanceof AddChildFragment)

            finish();
        else
            super.onBackPressed();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//    {
//        switch (menuItem.getItemId())
//        {
//            case R.id.home_menu:
//                HomeFragment homeFragment=new HomeFragment();
//                replaceFagment("Home",homeFragment, false);
//                break;
//
//            case R.id.add_child_menu:
//                AddChildFragment addChildFragment=new AddChildFragment();
//                replaceFagment("Add Child",addChildFragment, false);
//                break;
//
//            case R.id.view_provs:
//                ViewAllFragment viewAllFragment=new ViewAllFragment();
//                replaceFagment("All Children",viewAllFragment, false);
//                break;
//        }
//        return false;
//    }

//    private void replaceFagment(String tittle, Fragment fragment, boolean isAddToBackStack)
//    {
//        FragmentManager manager2 = getSupportFragmentManager();
//        FragmentTransaction transaction2=manager2.beginTransaction();
//        Bundle bundle2=new Bundle();
//        bundle2.putInt("id",Choir_ID);
//        fragment.setArguments(bundle2);
//
//        if(isAddToBackStack)
//            transaction2.replace(R.id.contain,fragment).addToBackStack(tittle).commit();
//
//        else
//            transaction2.replace(R.id.contain,fragment).commit();
//
//        getSupportActionBar().setTitle(tittle);
//    }


//    @Override
//    public void onBackPressed()
//    {
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contain);
//        assert currentFragment != null;
//        getSupportFragmentManager().beginTransaction().remove(currentFragment);
////        int fragmentSize = getSupportFragmentManager().getBackStackEntryCount();
////        if(fragmentSize > 1)
////            getSupportFragmentManager().popBackStack();
////
////        else
////            finish();
//    }


}
