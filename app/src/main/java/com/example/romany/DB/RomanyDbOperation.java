package com.example.romany.DB;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class RomanyDbOperation
{
    // CRUD for choir table
    public void insertAndUpdateChoir(ChoirModel newChoirObj)
    {
        newChoirObj.save();
    }

    public void deleteChoir(ChoirModel deletedChoir)
    {
        deletedChoir.delete();
    }

    public List<ChoirModel> selectAllChoirs()
    {
        return new Select().from(ChoirModel.class).queryList();
    }

    public List<ChoirModel> selectChoirByID(int selectedChoirId)
    {
        return new Select().from(ChoirModel.class).where(ChoirModel_Table.choirID.eq(selectedChoirId))
                .queryList();
    }


// ------------- CRUD for child table -----------------

    public List<ChildModel> selectAllChildrenForSameChoir(int choirId)
    {
        return new Select().from(ChildModel.class).where(ChildModel_Table.Choir_ID_choirID.eq(choirId))
                .queryList();
    }
    public void insertAndUpdateChild(ChildModel childModel)
    {
        childModel.save();
    }
    public void DeleteChild(ChildModel childModel)
    {
        childModel.delete();
    }
    public void DeleteAllChildren(int ChoirID)
    {
        new Delete().from(ChildModel.class).where(ChildModel_Table.Choir_ID_choirID.eq(ChoirID)).query();
    }
    public void UpdateChild(ChildModel childModel)
    {
        childModel.update();
    }
    public List<ChildModel> selectSpicifiecChildWithID(int ChildID)
    {
      return  new Select().from(ChildModel.class).where(ChildModel_Table.childId.eq(ChildID)).queryList();
    }
    public List<ChildProvaRelation> getNumberOfProvat(int id)
    {
        return new Select(ChildProvaRelation_Table.provaRelationObject_ProvaID).from(ChildProvaRelation.class)
                .where(ChildProvaRelation_Table.childRelationObject_childId.eq(id)).queryList();
    }

// --------------------CRUD for Prova Table-----------------
    public List<ProvaModel> SelectAllProvas(int ID)
    {
        return new Select().from(ProvaModel.class).where(ProvaModel_Table.provaChoirID_choirID.eq(ID))
                .queryList();
    }

    public void createProva(ProvaModel provaModel)
    {
        provaModel.save();
    }
    public void deleteProva(ProvaModel provaModel)
    {
        provaModel.delete();
    }

    public void deleteAllProvas(int ChoirID)
    {
        new Delete().from(ProvaModel.class).where(ProvaModel_Table.provaChoirID_choirID.eq(ChoirID)).query();
    }

    public void InsertChildrenInSpecificProva(ProvaModel provaModel,ChildModel childModel)
    {
        ChildProvaRelation childProvaRelation=new ChildProvaRelation();
        childProvaRelation.setChildRelationObject(childModel);
        childProvaRelation.setProvaRelationObject(provaModel);
        childProvaRelation.save();
    }
    public List<ChildProvaRelation> getChildrenInProva(int id)
    {
        return new Select(ChildProvaRelation_Table.childRelationObject_childId).from(ChildProvaRelation.class)
                .where(ChildProvaRelation_Table.provaRelationObject_ProvaID.eq(id)).queryList();
    }
    public List<ChildProvaRelation> selectLastDate(int Child_Id)
    {
        return new Select(ChildProvaRelation_Table.provaRelationObject_ProvaID).from(ChildProvaRelation.class)
                .where(ChildProvaRelation_Table.childRelationObject_childId.eq(Child_Id)).queryList();
    }
}
