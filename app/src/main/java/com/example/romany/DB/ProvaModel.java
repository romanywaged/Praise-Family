package com.example.romany.DB;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MarIvram.class)
public class ProvaModel extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column
    private int ProvaID;

    @Column
    private String ProvaName;


    @Column
    private String AddedTime;

    @ForeignKey(tableClass = ChoirModel.class
           ,onDelete = ForeignKeyAction.CASCADE
    )
    @Column
    private int provaChoirID;

    public void setProvaID(int provaID) {
        ProvaID = provaID;
    }

    public int getProvaChoirID() {
        return provaChoirID;
    }

    public void setProvaChoirID(int provaChoirID) {
        this.provaChoirID = provaChoirID;
    }

    public int getProvaID() {
        return ProvaID;
    }

    public String getProvaName() {
        return ProvaName;
    }

    public void setProvaName(String provaName) {
        ProvaName = provaName;
    }


    public String getAddedTime() {
        return AddedTime;
    }

    public void setAddedTime(String addedTime) {
        AddedTime = addedTime;
    }
}
