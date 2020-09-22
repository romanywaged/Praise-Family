package com.example.romany.DB;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MarIvram.class)
public class ChildModel extends BaseModel
{
    @PrimaryKey(autoincrement = true)
    @Column
    private int childId;

    @Column
    private String childName;


    @ForeignKey(tableClass = ChoirModel.class,
            onDelete = ForeignKeyAction.CASCADE)
    @Column
    public int Choir_ID;

    @Column
    private String Year;

    @Column
    private String Phone;


    @Column
    private String KhademName;

    public String getKhademName() {
        return KhademName;
    }

    public void setKhademName(String khademName) {
        KhademName = khademName;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getChoir_ID() {
        return Choir_ID;
    }

    public void setChoir_ID(int choir_ID) {
        Choir_ID = choir_ID;
    }
}
