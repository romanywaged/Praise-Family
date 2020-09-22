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

public class ProvaDetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;



    RomanyDbOperation dbOperation;
    ChildrenDetailsInProvaAdapter adapter;
    RecyclerView childrenRecycle;
    List<ChildModel> childModels;
    String ProvaName;
    public ProvaDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProvaDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProvaDetailFragment newInstance(String param1, String param2) {
        ProvaDetailFragment fragment = new ProvaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment=inflater.inflate(R.layout.activity_prova_details, container, false);
        childrenRecycle=(RecyclerView)fragment.findViewById(R.id.ProvaDetai_RV);
        childModels=new ArrayList<>();
        Bundle bundle=getArguments();
        int id=bundle.getInt("id");
        ProvaName=bundle.getString("provaName");
        dbOperation=new RomanyDbOperation();
        for (int i=0;i<dbOperation.getChildrenInProva(id).size();i++)
        {
            childModels.add(dbOperation.getChildrenInProva(id).get(i).getChildRelationObject());
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
        File file = new File(getActivity().getExternalFilesDir(null),ProvaName+".xls");
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
