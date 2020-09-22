package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.romany.R;
import com.example.romany.model.PagerClass;

import java.util.List;

public class SlidehomeAdapter extends PagerAdapter {
    Context context;
    List<PagerClass> pager;

    public SlidehomeAdapter(Context context, List<PagerClass> pager) {
        this.context = context;
        this.pager = pager;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.item_slide,null);
        ImageView slideImage= view.findViewById(R.id.imageView2);
        slideImage.setImageResource(pager.get(position).getIMG());
        container.addView(view);
        return  view;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
