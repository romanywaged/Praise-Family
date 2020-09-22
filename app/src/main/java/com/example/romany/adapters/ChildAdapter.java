package com.example.romany.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.DB.ChildModel;
import com.example.romany.R;
import com.example.romany.model.OnChildClicked;
import com.example.romany.model.TranemClass;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyChildHolder> implements Filterable {
    List<ChildModel>children;
    private List<ChildModel> filterdata;
    Context context;
    OnChildClicked clicked;
    public ChildAdapter( Context context,OnChildClicked childClicked) {
        this.context = context;
        this.clicked=childClicked;
    }

    @NonNull
    @Override
    public MyChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.updated_row,parent,false);
        return new MyChildHolder(view);
    }

    public void SetChildren(List<ChildModel> children)
    {
        this.children=children;
        this.filterdata=new ArrayList<>(children);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyChildHolder holder, final int position) {
        holder.ChlidName.setText(children.get(position).getChildName());
        holder.child_ID.setText(String.valueOf(children.get(position).getChildId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.clicked(children.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return children.size();
    }


    public ChildModel getChildAt(int position)
    {
        return children.get(position);
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

    public class MyChildHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView ChlidName,child_ID,ChilddPhone,ChildYearSchool,ChildNotes;
        View view;
        CardView cardView;
        ExpandableLinearLayout expandableLinearLayout;
        public MyChildHolder(@NonNull View itemView) {
            super(itemView);
            ChlidName=(TextView)itemView.findViewById(R.id.child_row_name);
            child_ID=(TextView)itemView.findViewById(R.id.child_id_txt);
            cardView=(CardView)itemView.findViewById(R.id.childActivity);
            cardView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),121,0,"Update Child");
            menu.add(this.getAdapterPosition(),122,1,"Delete Child");
        }
    }
}
