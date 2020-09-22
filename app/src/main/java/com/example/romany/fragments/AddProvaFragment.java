package com.example.romany.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.romany.DB.ChildModel;
import com.example.romany.DB.ProvaModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.AddProvaAdapter;
import com.example.romany.model.CommonFunctions;
import com.example.romany.model.OnCheckBoxClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AddProvaFragment extends Fragment implements OnCheckBoxClickListener
{
    private OnFragmentInteractionListener mListener;

    private static final String COIR_ID_KEY = "id";
    private int coirID;

    private List<ChildModel> Selected;
    private RomanyDbOperation dbOperation;
    private AddProvaAdapter adapter;
    private RecyclerView recyclerAddProva;
    private Button submit;
    private List<ChildModel> childModels;
    private ProvaModel provaModel = new ProvaModel();
    private Calendar calendar = Calendar.getInstance();
    private String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
    private String CurrentTime = simpleDateFormat.format(calendar.getTime());
    private CommonFunctions functions;

    public AddProvaFragment() {}

    public static AddProvaFragment newInstance(int coirId)
    {
        AddProvaFragment fragment = new AddProvaFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_add_prova, container, false);

        recyclerAddProva = (RecyclerView) view.findViewById(R.id.RV_AddProva);
        submit = (Button) view.findViewById(R.id.btn_submit);

        Selected = new ArrayList<>();
        dbOperation = new RomanyDbOperation();
        childModels = new ArrayList<>(dbOperation.selectAllChildrenForSameChoir(coirID));
        functions = new CommonFunctions();
        addProva();
        return view;
    }

    private void addProva() {
        submit.setOnClickListener(v ->
        {
            if (Selected.size() == 0)
                functions.ShowMessage(getActivity(), "Please Select Children to Add Prova");

            else {
                provaModel.setProvaName(currentDate);
                provaModel.setProvaChoirID(coirID);
                provaModel.setAddedTime(CurrentTime);
                dbOperation.createProva(provaModel);
                for (int i = 0; i < Selected.size(); i++) {
                    dbOperation.InsertChildrenInSpecificProva(provaModel, Selected.get(i));
                }
                functions.ShowMessage(getActivity(), "Created!");

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                HomeFragment home = new HomeFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", coirID);
//                home.setArguments(bundle);
//                transaction.replace(R.id.contain, home)
//                        .commit();
            }
        });
    }


    private void SortArray(List<ChildModel> childModels) {
        Collections.sort(childModels, new Comparator<ChildModel>() {
            @Override
            public int compare(ChildModel o1, ChildModel o2) {
                return o1.getChildName().compareTo(o2.getChildName());
            }
        });

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
    public void onStart() {
        adapter = new AddProvaAdapter(getActivity(), this);
        SortArray(childModels);
        adapter.setChildren(childModels);
        adapter.notifyDataSetChanged();
        recyclerAddProva.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAddProva.setAdapter(adapter);
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void click(CheckBox checkBox, View view, ChildModel childModel) {
        if (checkBox.isChecked()) {
            Selected.add(childModel);
        } else {
            Selected.remove(childModel);
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
