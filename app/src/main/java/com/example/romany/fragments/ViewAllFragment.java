package com.example.romany.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.ChoirModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.activities.ChildDBActivity;
import com.example.romany.adapters.ChildAdapter;
import com.example.romany.model.OnChildClicked;
import com.example.romany.model.TranemClass;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewAllFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllFragment extends Fragment implements OnChildClicked
{
    private static final String COIR_ID_KEY = "id";
    private int coirID;

    RomanyDbOperation dbOperation;
    RecyclerView RV_Child;
    TextView NoShow;
    ChildAdapter adapter;
    List<ChildModel>child;
    ChoirModel choirModel;
    private OnFragmentInteractionListener mListener;

    public ViewAllFragment() {}

    public static ViewAllFragment newInstance(int coirId)
    {
        ViewAllFragment fragment = new ViewAllFragment();
        Bundle args = new Bundle();
        args.putInt(COIR_ID_KEY, coirId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            coirID = getArguments().getInt(COIR_ID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view_all, container, false);
        RV_Child=(RecyclerView)view.findViewById(R.id.Child_RV);

        NoShow=(TextView)view.findViewById(R.id.NoShow_View);
        dbOperation=new RomanyDbOperation();
        choirModel=dbOperation.selectChoirByID(coirID).get(0);
        setHasOptionsMenu(true);

        adapter=new ChildAdapter(getActivity(),this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        child=new ArrayList<>(dbOperation.selectAllChildrenForSameChoir(coirID));
        if (child.size()!=0)
        {
            NoShow.setVisibility(View.INVISIBLE);
            RV_Child.setVisibility(View.VISIBLE);
        }
        adapter=new ChildAdapter(getActivity(),this);
        SortArray(child);
        adapter.SetChildren(child);
        adapter.notifyDataSetChanged();
        RV_Child.setLayoutManager(new LinearLayoutManager(getActivity()));
        RV_Child.setAdapter(adapter);
        super.onStart();
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

    @Override
    public void clicked(ChildModel childModel)
    {
        ChildInfoFragment ChildInfo = ChildInfoFragment.newInstance(childModel.getChildId());
        ((ChildDBActivity)getActivity()).replaceFragment(ChildInfo, ChildInfo.getTag(), true);

//        FragmentManager manager=getFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        ChildInfoFragment ChildInfo=new ChildInfoFragment();
//        Bundle bundle=new Bundle();
//        bundle.putInt("id",childModel.getChildId());
//        ChildInfo.setArguments(bundle);
//        transaction.replace(R.id.contain,ChildInfo)
//                .commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void SortArray(List<ChildModel> childModels)
    {
        Collections.sort(childModels, new Comparator<ChildModel>() {
            @Override
            public int compare(ChildModel o1, ChildModel o2) {
                return o1.getChildName().compareTo(o2.getChildName());
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_all_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.searchChildren);
        SearchView searchView=(SearchView)searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteAll:
                AlertDelete();
                return true;
            case R.id.createExcell:
                CreateExcell();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case 121:
                ChildModel childModel1=adapter.getChildAt(item.getGroupId());
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                UpdateFragment addProvaFrag=new UpdateFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",childModel1.getChildId());
                bundle.putInt("Choir",coirID);
                addProvaFrag.setArguments(bundle);
                transaction.replace(R.id.contain,addProvaFrag)
                        .commit();
                return true;
            case 122:
                ChildModel childModel=adapter.getChildAt(item.getGroupId());
                dbOperation.DeleteChild(childModel);
                Toast.makeText(getActivity(),"Child Deleted",Toast.LENGTH_LONG).show();
                child.clear();
                child=new ArrayList<>(dbOperation.selectAllChildrenForSameChoir(coirID));
                if (child.size()!=0)
                {
                    NoShow.setVisibility(View.INVISIBLE);
                    RV_Child.setVisibility(View.VISIBLE);
                }
                else {
                    NoShow.setVisibility(View.VISIBLE);
                    RV_Child.setVisibility(View.INVISIBLE);
                }
                adapter.SetChildren(child);
                adapter.notifyDataSetChanged();
                return true;
                default:
                    return super.onContextItemSelected(item);
        }

    }


    private void CreateExcell()
    {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Name of sheet");
        Cell cell = null;
        for (int i=0;i<child.size();i++)
        {
            Row row =sheet.createRow(i);

            cell=row.createCell(0);
            cell.setCellValue(child.get(i).getChildName());
            sheet.setColumnWidth(0,(15*500));

            cell=row.createCell(1);
            cell.setCellValue(child.get(i).getPhone());
            sheet.setColumnWidth(1,(15*500));

            cell=row.createCell(2);
            cell.setCellValue(child.get(i).getYear());
            sheet.setColumnWidth(2,(15*500));

            cell=row.createCell(3);
            cell.setCellValue(child.get(i).getKhademName());
            sheet.setColumnWidth(3,(15*500));
        }
        File file = new File(getActivity().getExternalFilesDir(null),choirModel.getChoirName()+".xls");
        FileOutputStream outputStream =null;
        try {
            outputStream=new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getActivity(),"Excell Sheet Created With Choir Name!",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void AlertDelete()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.icon)
                .setMessage("Are You Sure You Will Delete All Children And All Provas ")
                .setTitle("Praise Family")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbOperation.DeleteAllChildren(coirID);
                        dbOperation.deleteAllProvas(coirID);
                        child.clear();
                        NoShow.setVisibility(View.VISIBLE);
                        RV_Child.setVisibility(View.INVISIBLE);
                        adapter.SetChildren(child);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }


















}
