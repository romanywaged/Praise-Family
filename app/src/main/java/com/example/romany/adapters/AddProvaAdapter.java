package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ChildModel;
import com.example.romany.R;
import com.example.romany.model.OnCheckBoxClickListener;

import org.apache.poi.ss.usermodel.Color;

import java.util.ArrayList;
import java.util.List;

public class AddProvaAdapter extends RecyclerView.Adapter<AddProvaAdapter.MyAddProvaHolder> implements Filterable {
    private List<ChildModel>filterdata;
    private List<ChildModel>children;
    private Context context;
    private OnCheckBoxClickListener listener;
    public AddProvaAdapter( Context context,OnCheckBoxClickListener clickListener) {
        this.context = context;
        this.listener=clickListener;
    }

    @NonNull
    @Override
    public MyAddProvaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_prova_row,parent,false);
        return new MyAddProvaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAddProvaHolder holder, final int position) {
        holder.ChildName.setText(children.get(position).getChildName());
        holder.ChildID.setText(String.valueOf(children.get(position).getChildId()));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked())
                {
                    holder.checkBox.setChecked(false);
                }
                else {
                    holder.checkBox.setChecked(true);
                }

                listener.click(holder.checkBox,v,children.get(position));
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.click(holder.checkBox,v,children.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return children.size();
    }


    public void setChildren(List<ChildModel>children)
    {
        this.children=children;
        this.filterdata=new ArrayList<>(children);
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }
    private Filter examplefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChildModel> filteredList=new ArrayList<>();
            if (constraint==null||constraint.length()==0)
            {
                filteredList.addAll(filterdata);
            }
            else
            {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for (ChildModel model:filterdata)
                {
                    if (model.getChildName().toLowerCase().contains(filterpattern)
                            ||String.valueOf(model.getChildId()).toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(model);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            children.clear();
            children.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyAddProvaHolder extends RecyclerView.ViewHolder {
        TextView ChildName,ChildID;
        CheckBox checkBox;
        public MyAddProvaHolder(@NonNull View itemView) {
            super(itemView);
            ChildName=(TextView)itemView.findViewById(R.id.add_prova_row_name);
            checkBox=(CheckBox)itemView.findViewById(R.id.add_prova_row_check);
            ChildID=(TextView)itemView.findViewById(R.id.itemID);
        }
    }
}
