package com.example.romany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.romany.R;
import com.example.romany.model.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    List<Person>people;
    Context context;

    public PersonAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.updated_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.MyViewHolder holder, int position) {
        holder.v.setVisibility(View.GONE);
        holder.name.setText(people.get(position).getName());
        holder.id.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,id;
        View v;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.child_row_name);
            id=(TextView)itemView.findViewById(R.id.child_id_txt);
        }
    }
}
