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
import com.example.romany.activities.ChildDBActivity;

import java.util.ArrayList;
import java.util.List;

public class ChildInfoFragment extends Fragment
{
    private static final String COIR_ID_KEY = "id";
    private int coirID;

    private OnFragmentInteractionListener mListener;
    private RomanyDbOperation dbOperation;
    private TextView Name,Phone,Khadem,Last_Prova,num_of_provat,School;
    private static final int RequestCall=1;

    public ChildInfoFragment() {}

    public static ChildInfoFragment newInstance(int coirId) {
        ChildInfoFragment fragment = new ChildInfoFragment();
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
       View frag=inflater.inflate(R.layout.fragment_child_info, container, false);

       Name=frag.findViewById(R.id.txt_name);
       num_of_provat=frag.findViewById(R.id.txt_numofProvs);
       Last_Prova=frag.findViewById(R.id.txt_last_prova);
       Khadem=frag.findViewById(R.id.txt_Khadem);
       Phone=frag.findViewById(R.id.txt_phone_number);
       School=frag.findViewById(R.id.txt_School_year);

       dbOperation=new RomanyDbOperation();
       List<ProvaModel> provaModels=new ArrayList<>();
       final ChildModel childModel=dbOperation.selectSpicifiecChildWithID(coirID).get(0);
        for (int i=0;i<dbOperation.selectLastDate(coirID).size();i++)
        {
            provaModels.add(dbOperation.selectLastDate(coirID).get(i).getProvaRelationObject());
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

       Phone.setOnClickListener(v -> MakeCall());

       Last_Prova.setOnClickListener(view ->
       {
           ChildHistoryFragment Child_H = ChildHistoryFragment.newInstance(coirID);
           ((ChildDBActivity)getActivity()).replaceFragment(Child_H, Child_H.getTag(), true);

//               FragmentManager manager=getFragmentManager();
//               FragmentTransaction transaction=manager.beginTransaction();
//               ChildHistoryFragment Child_H=new ChildHistoryFragment();
//               Bundle bundle=new Bundle();
//               bundle.putInt("id",coirID);
//               Child_H.setArguments(bundle);
//               transaction.replace(R.id.contain,Child_H).addToBackStack(null)
//                       .commit();
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
