package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ChoirModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.RomanyApplication;
import com.example.romany.activities.AllChoirs;
import com.example.romany.model.OnChoirClick;

import java.util.List;

public class ChoirAdapter extends RecyclerView.Adapter<ChoirAdapter.MyChoirView> {
    List<ChoirModel>choirModels;
    Context ncontext;
    OnChoirClick choirClick;

    public ChoirAdapter( Context ncontext,OnChoirClick clicked) {
        this.ncontext = ncontext;
        this.choirClick=clicked;
    }



    @NonNull
    @Override
    public MyChoirView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ncontext).inflate(R.layout.choir_row,parent,false);
        return new MyChoirView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChoirView holder, final int position) {
        holder.ChoirName.setText(choirModels.get(position).getChoirName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choirClick.clicked(choirModels.get(position));
            }
        });
    }
    public ChoirModel choirAt(int postion)
    {
        return choirModels.get(postion);
    }
    public void SetChoirs(List<ChoirModel>choirModels)
    {
        this.choirModels=choirModels;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return choirModels.size();
    }

    public class MyChoirView extends RecyclerView.ViewHolder {
        TextView ChoirName;
        public MyChoirView(@NonNull View itemView) {
            super(itemView);
            ChoirName=(TextView)itemView.findViewById(R.id.choir_row_name);
        }
    }
}
