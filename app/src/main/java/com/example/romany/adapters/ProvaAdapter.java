package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ProvaModel;
import com.example.romany.R;
import com.example.romany.model.OnProvaSelectedListener;

import java.util.List;

public class ProvaAdapter extends RecyclerView.Adapter<ProvaAdapter.MyProvaHolder> {
    List<ProvaModel> provas;
    Context context;
    OnProvaSelectedListener listener;
    public ProvaAdapter( Context context,OnProvaSelectedListener clicked) {
        this.context = context;
        this.listener=clicked;
    }

    @NonNull
    @Override
    public ProvaAdapter.MyProvaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.prova_row,parent,false);
        return new MyProvaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvaAdapter.MyProvaHolder holder, final int position) {

        holder.prova_date.setText(provas.get(position).getProvaName());
        holder.provaTime.setText(provas.get(position).getAddedTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ProvaClicked(provas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return provas.size();
    }

    public void setProva(List<ProvaModel>prova)
    {
        notifyDataSetChanged();
        this.provas=prova;
    }
    public ProvaModel ProvaAt(int position)
    {
        return provas.get(position);
    }

    public class MyProvaHolder extends RecyclerView.ViewHolder {
        TextView prova_date,provaTime;
        public MyProvaHolder(@NonNull View itemView) {
            super(itemView);
            prova_date=(TextView)itemView.findViewById(R.id.Prova_Date);
            provaTime=(TextView)itemView.findViewById(R.id.Prova_Time);
        }
    }
}
