package com.example.romany.DB;

import android.widget.Toast;

import com.example.romany.model.TranemClass;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = MarIvram.class,useBooleanGetterSetters = true)
public class Hymns extends BaseModel {
    @Column
    @PrimaryKey
    private String Hymns_id;

    @Column
    private String Hymns_name;

    @Column
    private String Hymns_word;

    @Column
    private String Hymns_url;

    public String getHymns_id() {
        return Hymns_id;
    }

    public void setHymns_id(String hymns_id) {
        Hymns_id = hymns_id;
    }

    public String getHymns_name() {
        return Hymns_name;
    }

    public void setHymns_name(String hymns_name) {
        Hymns_name = hymns_name;
    }

    public String getHymns_word() {
        return Hymns_word;
    }

    public void setHymns_word(String hymns_word) {
        Hymns_word = hymns_word;
    }

    public String getHymns_url() {
        return Hymns_url;
    }

    public void setHymns_url(String hymns_url) {
        Hymns_url = hymns_url;
    }
}
