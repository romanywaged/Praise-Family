package com.example.romany.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.romany.DB.ProvaModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChildHistoryFragment extends Fragment
{
    private static final String COIR_ID_KEY = "id";
    private int coirID;

    private RomanyDbOperation dbOperation;
    private HistoryAdapter adapter;
    private RecyclerView Provas;

    public ChildHistoryFragment() {}

    public static ChildHistoryFragment newInstance(int coirId) {
        ChildHistoryFragment fragment = new ChildHistoryFragment();
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
        View frag=inflater.inflate(R.layout.fragment_child_history, container, false);

        dbOperation=new RomanyDbOperation();
        List<ProvaModel> provaModels=new ArrayList<>();
        for (int i=0;i<dbOperation.selectLastDate(coirID).size();i++)
        {
            provaModels.add(dbOperation.selectLastDate(coirID).get(i).getProvaRelationObject());
        }
        adapter=new HistoryAdapter(provaModels,getActivity());
        Provas=frag.findViewById(R.id.prove_History);
        Provas.setLayoutManager(new LinearLayoutManager(getActivity()));
        Provas.setAdapter(adapter);
        return frag;
    }
}