package com.example.romany.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romany.DB.ChildModel;
import com.example.romany.DB.ProvaModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;

import java.util.ArrayList;
import java.util.List;

public class ChildInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int Child_ID;
    private RomanyDbOperation dbOperation;
    private TextView Name,Phone,Khadem,Last_Prova,num_of_provat,School;
    private static final int RequestCall=1;
    public ChildInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildInfoFragment newInstance(String param1, String param2) {
        ChildInfoFragment fragment = new ChildInfoFragment();
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
       View frag=inflater.inflate(R.layout.fragment_child_info, container, false);

       Name=frag.findViewById(R.id.txt_name);
       num_of_provat=frag.findViewById(R.id.txt_numofProvs);
       Last_Prova=frag.findViewById(R.id.txt_last_prova);
       Khadem=frag.findViewById(R.id.txt_Khadem);
       Phone=frag.findViewById(R.id.txt_phone_number);
       School=frag.findViewById(R.id.txt_School_year);

       Bundle bundle=getArguments();
       Child_ID=bundle.getInt("id");
       dbOperation=new RomanyDbOperation();
       List<ProvaModel> provaModels=new ArrayList<>();
       final ChildModel childModel=dbOperation.selectSpicifiecChildWithID(Child_ID).get(0);
        for (int i=0;i<dbOperation.selectLastDate(Child_ID).size();i++)
        {
            provaModels.add(dbOperation.selectLastDate(Child_ID).get(i).getProvaRelationObject());
        }
        int u=provaModels.size();
        if (provaModels.size()!=0||u!=0) {
            ProvaModel provaModel = provaModels.get(provaModels.size() - 1);
            String g=provaModel.getProvaName();
            String y=g;
            Last_Prova.setText(g);
            num_of_provat.setText(String.valueOf(u));
        }
       Name.setText(childModel.getChildName());
       Phone.setText(childModel.getPhone());
       Khadem.setText(childModel.getKhademName());
       School.setText(childModel.getYear());

       Phone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MakeCall();
           }
       });

       Last_Prova.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentManager manager=getFragmentManager();
               FragmentTransaction transaction=manager.beginTransaction();
               ChildHistoryFragment Child_H=new ChildHistoryFragment();
               Bundle bundle=new Bundle();
               bundle.putInt("id",Child_ID);
               Child_H.setArguments(bundle);
               transaction.replace(R.id.contain,Child_H).addToBackStack(null)
                       .commit();
           }
       });
        return frag;
    }


    private void MakeCall()
    {
                final String Pp=Phone.getText().toString();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CALL_PHONE},RequestCall);
                }
                else
                {
                    String dial="tel:"+Pp;
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
                }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==RequestCall)
        {
            if (grantResults.length > 0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                MakeCall();
            }
            else
            {
                Toast.makeText(getActivity(),"NOoooo",Toast.LENGTH_LONG).show();
            }
        }
    }
}
