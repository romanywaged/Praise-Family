package com.example.romany.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.model.CommonFunctions;
import com.google.android.material.textfield.TextInputEditText;


public class UpdateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;



    private TextInputEditText person_name,person_phone,person_year,TxtKhademName;
    private Button update;
    private RomanyDbOperation dbOperation;
    private int ChildID;
    private int ChoirID;
    private CommonFunctions functions;
    public UpdateFragment() {
    }


    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
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
        View fragment=inflater.inflate(R.layout.fragment_update, container, false);
        person_name=fragment.findViewById(R.id.update_person_name);
        person_phone=(TextInputEditText)fragment.findViewById(R.id.update_phone);
        person_year=(TextInputEditText)fragment.findViewById(R.id.update_school_year);
        TxtKhademName=(TextInputEditText)fragment.findViewById(R.id.update_KhdemNameAddChild);
        update=(Button)fragment.findViewById(R.id.update_btn);
        Bundle bundle=getArguments();
        ChildID=bundle.getInt("id");
        ChoirID=bundle.getInt("Choir");
        dbOperation=new RomanyDbOperation();
        ChildModel childModel=dbOperation.selectSpicifiecChildWithID(ChildID).get(0);
        person_name.setText(childModel.getChildName());
        person_phone.setText(childModel.getPhone());
        person_year.setText(childModel.getYear());
        TxtKhademName.setText(childModel.getKhademName());

        functions=new CommonFunctions();
        Update(ChoirID,childModel);
        return fragment;
    }

    private void Update(final int i, final ChildModel childModel) {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (
                            person_name.getText().toString().trim().equals("") ||
                                    person_phone.getText().toString().trim().equals("") ||
                                    person_year.getText().toString().trim().equals("")||
                                    TxtKhademName.getText().toString().trim().equals("")){
                        functions.ShowMessage(getActivity(), "Please Fill All Fields");
                    }
                    else
                    {
                        if (person_phone.getText().length()!=11)
                        {
                            person_phone.setError("Please Put Real Phone Number");
                        }
                        else
                        {
                            String name = person_name.getText().toString().trim();
                            String Phone = person_phone.getText().toString().trim();
                            String Year = person_year.getText().toString().trim();
                            String note = TxtKhademName.getText().toString().trim();
                            childModel.setChildName(name);
                            childModel.setChoir_ID(i);
                            childModel.setPhone(Phone);
                            childModel.setKhademName(note);
                            childModel.setYear(Year);
                            dbOperation.UpdateChild(childModel);
                            person_name.setEnabled(false);
                            person_phone.setEnabled(false);
                            person_year.setEnabled(false);
                            TxtKhademName.setEnabled(false);
                            functions.ShowMessage(getActivity(),"Updated!");
                            btn_Clicked();
                        }
                    }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public void btn_Clicked()
    {
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        ViewAllFragment viewAll=new ViewAllFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",ChoirID);
        viewAll.setArguments(bundle);
        transaction.replace(R.id.contain,viewAll).addToBackStack("")
                .commit();
    }
}
