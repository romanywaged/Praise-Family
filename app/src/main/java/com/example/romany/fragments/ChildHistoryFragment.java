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


public class ChildHistoryFragment extends Fragment {

    private int Child_ID;
    private RomanyDbOperation dbOperation;
    private HistoryAdapter adapter;
    private RecyclerView Provas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildHistoryFragment newInstance(String param1, String param2) {
        ChildHistoryFragment fragment = new ChildHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag=inflater.inflate(R.layout.fragment_child_history, container, false);

        Bundle bundle=getArguments();
        Child_ID=bundle.getInt("id");
        dbOperation=new RomanyDbOperation();
        List<ProvaModel> provaModels=new ArrayList<>();
        for (int i=0;i<dbOperation.selectLastDate(Child_ID).size();i++)
        {
            provaModels.add(dbOperation.selectLastDate(Child_ID).get(i).getProvaRelationObject());
        }
        adapter=new HistoryAdapter(provaModels,getActivity());
        Provas=frag.findViewById(R.id.prove_History);
        Provas.setLayoutManager(new LinearLayoutManager(getActivity()));
        Provas.setAdapter(adapter);
        return frag;
    }
}