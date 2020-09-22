package com.example.romany.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.romany.R;

/**
 * Created by Marina Wageed on 22,September,2020
 * Trufla Technology,
 * Cairo, Egypt.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    protected abstract int getFragmentPlaceHolder();

    public void replaceFragment(Fragment nextFragment, String tag, boolean addToBackStack)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(getFragmentPlaceHolder(), nextFragment);
        if(addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void addNewFragment()
    {

    }

    public void removeCurrentFragment()
    {

    }
}
