package com.example.romany.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.ChildrenDetailsInProvaAdapter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProvaDetailFragment extends Fragment
{
    private static final String PROVA_ID_KEY = "prova_id";
    private static final String PROVA_NAME_KEY = "prova_name";

    private int provaID;
    private String provaName;


    private OnFragmentInteractionListener mListener;

    RomanyDbOperation dbOperation;
    ChildrenDetailsInProvaAdapter adapter;
    RecyclerView childrenRecycle;
    List<ChildModel> childModels;

    public ProvaDetailFragment() {}

    public static ProvaDetailFragment newInstance(int coirId, String provaName)
    {
        ProvaDetailFragment fragment = new ProvaDetailFragment();
        Bundle args = new Bundle();
        args.putInt(PROVA_ID_KEY, coirId);
        args.putString(PROVA_NAME_KEY, provaName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            provaID = getArguments().getInt(PROVA_ID_KEY);
            provaName = getArguments().getString(PROVA_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragment=inflater.inflate(R.layout.activity_prova_details, container, false);
        childrenRecycle=(RecyclerView)fragment.findViewById(R.id.ProvaDetai_RV);
        childModels=new ArrayList<>();

        dbOperation=new RomanyDbOperation();
        for (int i=0;i<dbOperation.getChildrenInProva(provaID).size();i++)
        {
            childModels.add(dbOperation.getChildrenInProva(provaID).get(i).getChildRelationObject());
        }
        adapter=new ChildrenDetailsInProvaAdapter(childModels, getActivity());
        childrenRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        childrenRecycle.setAdapter(adapter);
        setHasOptionsMenu(true);
        return fragment;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void CreateExcell()
    {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Name of sheet");
        Cell cell = null;
        for (int i=0;i<childModels.size();i++)
        {
            Row row =sheet.createRow(i);

            cell=row.createCell(0);
            cell.setCellValue(childModels.get(i).getChildName());
            sheet.setColumnWidth(0,(15*500));
        }

        File file = new File(getActivity().getExternalFilesDir(null),provaName+".xls");
        FileOutputStream outputStream =null;
        try {
            outputStream=new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getActivity(),"Excell Created With Prova Name!",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Erorr!",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.saveProva:
                CreateExcell();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
