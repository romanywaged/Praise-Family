package com.example.romany.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.R;
import com.example.romany.activities.ShowTarnema;
import com.example.romany.model.OnTarnemaCliked;
import com.example.romany.model.TranemClass;

import java.util.ArrayList;
import java.util.List;

public class TranemAdapter extends RecyclerView.Adapter<TranemAdapter.MarIvramViewHolder> implements Filterable {
    private List<TranemClass> mlist;
    private List<TranemClass> filterdata;
    private Context mcontext;
    private OnTarnemaCliked cliked;
    Animation fade;

    public TranemAdapter(List<TranemClass> mlist, Context mcontext,OnTarnemaCliked cliked) {
        this.mlist = mlist;
        this.mcontext = mcontext;
        filterdata=new ArrayList<>(mlist);
        this.cliked=cliked;
    }
    @NonNull
    @Override
    public MarIvramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hymns_row,parent,false);
        return new TranemAdapter.MarIvramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MarIvramViewHolder holder, final int position) {
        fade= AnimationUtils.loadAnimation(mcontext,R.anim.scale_animition);
        holder.cardView.setAnimation(fade);
        holder.tranim_name.setText(mlist.get(position).getHymnsName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliked.isclicked(mlist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }
    private Filter examplefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TranemClass> filteredList=new ArrayList<>();
            if (constraint==null||constraint.length()==0)
            {
                filteredList.addAll(filterdata);
            }
            else
            {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for (TranemClass person:filterdata)
                {
                    if (person.getHymnsName().toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(person);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mlist.clear();
            mlist.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MarIvramViewHolder extends RecyclerView.ViewHolder {
        TextView tranim_name;
        CardView cardView;
        public MarIvramViewHolder(@NonNull View itemView) {
            super(itemView);
            tranim_name = (TextView) itemView.findViewById(R.id.person_view);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}
