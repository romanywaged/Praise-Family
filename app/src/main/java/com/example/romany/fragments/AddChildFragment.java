package com.example.romany.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.model.CommonFunctions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoast.StyleableToast;
import com.raizlabs.android.dbflow.sql.language.Condition;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddChildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddChildFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

      private   TextInputEditText person_name,person_phone,person_year,TxtKhademName;
      private   Button add;
      private   RomanyDbOperation dbOperation;
      private   OnFragmentInteractionListener mListener;
      private CommonFunctions functions;



      public AddChildFragment() {
        // Required empty public constructor
      }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddChildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddChildFragment newInstance(String param1, String param2) {
        AddChildFragment fragment = new AddChildFragment();
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
       View view=inflater.inflate(R.layout.activity_add_child,container,false);
        person_name=(TextInputEditText)view.findViewById(R.id.person_name);
        person_phone=(TextInputEditText)view.findViewById(R.id.phone);
        person_year=(TextInputEditText)view.findViewById(R.id.school_year);
        TxtKhademName=(TextInputEditText)view.findViewById(R.id.KhdemNameAddChild);
        add=(Button)view.findViewById(R.id.Add_btn);
        dbOperation=new RomanyDbOperation();
        Bundle bundle=getArguments();
        int Choir_ID=bundle.getInt("id");
        functions=new CommonFunctions();
        addPerson(Choir_ID,view);
        return view;
    }

    private void addPerson(final int i, final View view) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
