package com.example.romany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

/*import com.example.romany.DB.Choir;

import com.example.romany.adapters.ChoirAdapter;*/
import com.example.romany.DB.ChoirModel;
import com.example.romany.DB.RomanyDbOperation;
import com.example.romany.R;
import com.example.romany.adapters.ChoirAdapter;
import com.example.romany.model.OnChoirClick;

import java.util.ArrayList;
import java.util.List;

public class AllChoirs extends AppCompatActivity implements OnChoirClick {
    ChoirAdapter adapter;
    RomanyDbOperation DB;
    RecyclerView choir_Recycle;
    List<ChoirModel>choirModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_choirs);
        choir_Recycle=(RecyclerView)findViewById(R.id.Choir_RV);
        DB=new RomanyDbOperation();
        choirModels=new ArrayList<>(DB.selectAllChoirs());
        adapter=new ChoirAdapter(AllChoirs.this,this);
        choir_Recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter.SetChoirs(choirModels);
        choir_Recycle.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clicked(ChoirModel choirModel) {
        Intent intent=new Intent(AllChoirs.this,ChildDBActivity.class);
        intent.putExtra("ChoirID",choirModel.getChoirID());
        intent.putExtra("ChoirName",choirModel.getChoirName());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AllChoirs.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
