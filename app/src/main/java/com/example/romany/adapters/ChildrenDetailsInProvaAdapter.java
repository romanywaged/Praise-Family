package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ChildModel;
import com.example.romany.R;

import java.util.ArrayList;
import java.util.List;

public class ChildrenDetailsInProvaAdapter extends RecyclerView.Adapter<ChildrenDetailsInProvaAdapter.MyDetailHolder> implements Filterable {
    List<ChildModel>children;
    private List<ChildModel>filterdata;
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
