package com.example.romany.DB;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = MarIvram.class)
public class ChildProvaRelation extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column
    private int ID;

    @ForeignKey(tableClass = ChildModel.class,
            onDelete = ForeignKeyAction.CASCADE
    )

    @Column
    ChildModel childRelationObject;

    @ForeignKey(tableClass = ProvaModel.class,
            onDelete = ForeignKeyAction.CASCADE
    )
    @Column
    ProvaModel provaRelationObject;

    public ChildModel getChildRelationObject() {
        return childRelationObject;
    }

    public void setChildRelationObject(ChildModel childRelationObject) {
        this.childRelationObject = childRelationObject;
    }

    public ProvaModel getProvaRelationObject() {
        return provaRelationObject;
    }

    public void setProvaRelationObject(ProvaModel provaRelationObject) {
        this.provaRelationObject = provaRelationObject;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
