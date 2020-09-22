package com.example.romany.DB;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MarIvram.class)
public class ChoirModel extends BaseModel
{
    @PrimaryKey(autoincrement = true)
    @Column
    private int choirID;

    @Column
    private String choirName;

    public int getChoirID() {
        return choirID;
    }

    public void setChoirID(int choirID) {
        this.choirID = choirID;
    }

    public String getChoirName() {
        return choirName;
    }

    public void setChoirName(String choirName) {
        this.choirName = choirName;
    }
}
