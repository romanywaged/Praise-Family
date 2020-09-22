package com.example.romany.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.romany.DB.ProvaModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.ProvaAdapter;
import com.example.romany.model.OnProvaSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnProvaSelectedListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private RecyclerView provas;
    private List<ProvaModel>mlist;
    int Choir_ID;
    private ProvaAdapter adapter;
    private FloatingActionButton addProva;
    private RomanyDbOperation dbOperation;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View fragment=inflater.inflate(R.layout.fragment_home, container, false);
        provas=(RecyclerView)fragment.findViewById(R.id.Prova_RV);
        addProva=(FloatingActionButton)fragment.findViewById(R.id.createProva_btn);
        Bundle bundle=getArguments();
        Choir_ID=bundle.getInt("id");
        dbOperation=new RomanyDbOperation();
        mlist=new ArrayList<>(dbOperation.SelectAllProvas(Choir_ID));
        AddProva(Choir_ID);
        deleteProvaFromDB();
        setHasOptionsMenu(true);
        return fragment;
    }

    private void AddProva(final int choir_id) {
        addProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                AddProvaFragment addProvaFrag=new AddProvaFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",choir_id);
                addProvaFrag.setArguments(bundle);
                transaction.replace(R.id.contain,addProvaFrag).addToBackStack(null)
                        .commit();

            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void deleteProvaFromDB()
    {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dbOperation.deleteProva(adapter.ProvaAt(viewHolder.getAdapterPosition()));
                mlist=new ArrayList<>(dbOperation.SelectAllProvas(Choir_ID));
                adapter.setProva(mlist);
                Toast.makeText(getActivity(),"Prova Deleted",Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(provas);
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
    public void onStart() {
        super.onStart();
        adapter=new ProvaAdapter(getActivity(),this);
        adapter.setProva(mlist);
        adapter.notifyDataSetChanged();
        provas.setLayoutManager(new LinearLayoutManager(getActivity()));
        provas.setAdapter(adapter);
    }

    @Override
    public void ProvaClicked(ProvaModel provaModel) {
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        ProvaDetailFragment addProvaFrag=new ProvaDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",provaModel.getProvaID());
        bundle.putString("provaName",provaModel.getProvaName());
        addProvaFrag.setArguments(bundle);
        transaction.replace(R.id.contain,addProvaFrag)
                .commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_delete_all,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.HomeDeleteAll:
                dbOperation.deleteAllProvas(Choir_ID);
                mlist.clear();
                adapter.setProva(mlist);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
