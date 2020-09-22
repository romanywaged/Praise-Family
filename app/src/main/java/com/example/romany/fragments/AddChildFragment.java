package com.example.romany.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.model.CommonFunctions;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddChildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddChildFragment extends Fragment {

    private static final String COIR_ID_KEY = "id";
    private int coirID;

    private   TextInputEditText person_name,person_phone,person_year,TxtKhademName;
    private   Button add;
    private   RomanyDbOperation dbOperation;
    private   OnFragmentInteractionListener mListener;
    private CommonFunctions functions;

    public AddChildFragment() {}

    public static AddChildFragment newInstance(int coirId)
    {
        AddChildFragment fragment = new AddChildFragment();
        Bundle args = new Bundle();
        args.putInt(COIR_ID_KEY, coirId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            coirID = getArguments().getInt(COIR_ID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       View view=inflater.inflate(R.layout.activity_add_child,container,false);

        person_name=(TextInputEditText)view.findViewById(R.id.person_name);
        person_phone=(TextInputEditText)view.findViewById(R.id.phone);
        person_year=(TextInputEditText)view.findViewById(R.id.school_year);
        TxtKhademName=(TextInputEditText)view.findViewById(R.id.KhdemNameAddChild);
        add=(Button)view.findViewById(R.id.Add_btn);

        dbOperation=new RomanyDbOperation();
        functions=new CommonFunctions();
        addPerson(coirID,view);

        return view;
    }

    private void addPerson(final int i, final View view)
    {
        add.setOnClickListener(v -> {
                if (
                        person_name.getText().toString().trim().equals("") ||
                                person_phone.getText().toString().trim().equals("") ||
                                person_year.getText().toString().trim().equals("")||
                                TxtKhademName.getText().toString().trim().equals("")){
                          functions.ShowMessage(getActivity(),"Please Fill All Fields");
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
                        ChildModel childModel = new ChildModel();
                        childModel.setChildName(name);
                        childModel.setChoir_ID(i);
                        childModel.setPhone(Phone);
                        childModel.setKhademName(note);
                        childModel.setYear(Year);
                        dbOperation.insertAndUpdateChild(childModel);
                        person_name.getText().clear();
                        person_phone.getText().clear();
                        person_year.getText().clear();
                        TxtKhademName.getText().clear();
                        functions.ShowMessage(getActivity(),"Added");
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
}
