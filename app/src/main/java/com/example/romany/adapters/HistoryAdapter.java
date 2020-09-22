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

import org.apache.poi.ss.formula.functions.Na;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHistoryView> {
    private List<ProvaModel>provaModels;
    Context context;
    public HistoryAdapter(List<ProvaModel> provaModels,Context context)
    {
        this.provaModels=provaModels;
        this.context=context;
    }
    @NonNull
    @Override
    public MyHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.prova_row,parent,false);
        return new MyHistoryView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryView holder, int position) {
        holder.Name.setText(provaModels.get(position).getProvaName());
        holder.Time.setText(provaModels.get(position).getAddedTime());
    }

    @Override
    public int getItemCount() {
        return provaModels.size();
    }

    public class MyHistoryView extends RecyclerView.ViewHolder {
        TextView Name,Time;
        public MyHistoryView(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.Prova_Date);
            Time=itemView.findViewById(R.id.Prova_Time);
        }
    }
}
