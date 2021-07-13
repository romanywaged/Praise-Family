package com.example.romany.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.example.romany.DB.ChildModel;

public interface OnCheckBoxClickListener {
    void click(ImageView checked, CardView cardView, ChildModel childModel);
}
