package com.example.romany.model;

import android.view.View;
import android.widget.CheckBox;

import com.example.romany.DB.ChildModel;

public interface OnCheckBoxClickListener {
    void click(CheckBox checkBox, View view, ChildModel childModel);
}
