package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ChildModel;
import com.example.romany.R;

import java.util.List;

public class ChildrenDetailsInProvaAdapter extends RecyclerView.Adapter<ChildrenDetailsInProvaAdapter.MyDetailHolder> {
    List<ChildModel>children;
    Context context;

    public ChildrenDetailsInProvaAdapter(List<ChildModel> children, Context context) {
        this.children = children;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildrenDetailsInProvaAdapter.MyDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(context).inflate(R.layout.add_prova_row,parent,false);
        return new MyDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenDetailsInProvaAdapter.MyDetailHolder holder, int position) {
            holder.textName.setText(children.get(position).getChildName());
            holder.ID.setText(String.valueOf(children.get(position).getChildId()));
            holder.checkBox.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public class MyDetailHolder extends RecyclerView.ViewHolder {
        TextView textName,ID;
        CheckBox checkBox;
        public MyDetailHolder(@NonNull View itemView) {
            super(itemView);
           textName=(TextView)itemView.findViewById(R.id.add_prova_row_name);
            checkBox=(CheckBox)itemView.findViewById(R.id.add_prova_row_check);
            ID=(TextView)itemView.findViewById(R.id.itemID);
        }
    }
}
