package com.example.romany.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.romany.DB.Hymns;
import com.example.romany.R;
import com.example.romany.adapters.TranemAdapter;
import com.example.romany.model.CommonFunctions;
import com.example.romany.model.OnTarnemaCliked;
import com.example.romany.model.TranemClass;
import com.google.android.material.snackbar.Snackbar;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ShowTranemActivity extends AppCompatActivity implements OnTarnemaCliked {
    private JsonArrayRequest arrayRequest;
    private RequestQueue queue;
    private List<TranemClass> lstTranim;
    private RecyclerView Rv;
    private TranemAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private CommonFunctions functions;
    private AlertDialog waitingDialod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtranem);
        initView();
        jsonRequest();
        functions=new CommonFunctions();
        functions.CheckConnection(getApplicationContext(),waitingDialod);
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



    //Define Design And RefreshLayout With Refresh
    private void initView()
    {
        Rv=(RecyclerView)findViewById(R.id.tranim);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.pink);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        waitingDialod=new SpotsDialog(this);
        lstTranim=new ArrayList<>();
        getSupportActionBar().setTitle("Praise Family");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ShowTranemActivity.this,"Updated!",Toast.LENGTH_LONG).show();
                functions.CheckConnection(getApplicationContext(),waitingDialod);
                jsonRequest();
                refreshLayout.setRefreshing(false);
            }
        });


        waitingDialod.show();
    }


    //json Request For Get List Of Traneem
    //Save in Local Data Base
    private void jsonRequest() {
        String jason_url="http://tranem.somee.com/get_TranemName/1";
        arrayRequest=new JsonArrayRequest(jason_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object=null;
                lstTranim.clear();
                for (int i=0;i<response.length();i++)
                {
                    try {
                        object=response.getJSONObject(i);
                        TranemClass tranemClass =new TranemClass();
                        tranemClass.setHymnsName(object.getString("Name"));
                        tranemClass.setId(object.getString("Id"));
                        tranemClass.setUrl(object.getString("Url"));
                        tranemClass.setWord(object.getString("Words"));
                        //Data Base Object And Save In DataBase
                        Hymns hymns=new Hymns();
                        hymns.setHymns_name(object.getString("Name"));
                        hymns.setHymns_url(object.getString("Url"));
                        hymns.setHymns_id(object.getString("Id"));
                        hymns.setHymns_word(object.getString("Words"));
                        hymns.save();
                        lstTranim.add(tranemClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               waitingDialod.dismiss();
                setuprecycle(lstTranim);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitingDialod.dismiss();
                setuprecycle(Select());
            }
        });
        queue= Volley.newRequestQueue(ShowTranemActivity.this);
        queue.add(arrayRequest);
    }




    //Set up View To Recycle
    private void setuprecycle(List<TranemClass> lstTranim) {
        adapter =new TranemAdapter(lstTranim,this,this);
        SortArray(lstTranim);
        adapter.notifyDataSetChanged();
        Rv.setLayoutManager(new LinearLayoutManager(this));
        Rv.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ShowTranemActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }



    //Create Menu For Search View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.search2);
        SearchView searchView=(SearchView)searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }



    //Select From Local DataBase
    private List<TranemClass> Select()
    {
        List<Hymns> hymnsList = new ArrayList<>();
        List<TranemClass> tarnema = new ArrayList<>();
        hymnsList = SQLite.select().from(Hymns.class).queryList();

       for (int i=0; i<hymnsList.size(); i++)
        {
            TranemClass myTarnima = new TranemClass();
            myTarnima.setId(hymnsList.get(i).getHymns_name());
            myTarnima.setHymnsName(hymnsList.get(i).getHymns_name());
            myTarnima.setUrl(hymnsList.get(i).getHymns_url());
            myTarnima.setWord(hymnsList.get(i).getHymns_word());
            tarnema.add(myTarnima);
        }
        return tarnema;
    }


    //Sort Tranem List
    private void SortArray(List<TranemClass> tarnem)
    {
        Collections.sort(tarnem, new Comparator<TranemClass>() {
            @Override
            public int compare(TranemClass o1, TranemClass o2) {
                return o1.getHymnsName().compareTo(o2.getHymnsName());
            }
        });

    }

    //Send Tarnema Obj To Show Tarnema Activity
    @Override
    public void isclicked(TranemClass tranemClass) {
        Intent showtarnema=new Intent(ShowTranemActivity.this, ShowTarnema.class);
        showtarnema.putExtra("Name",tranemClass.getHymnsName());
        showtarnema.putExtra("Id",tranemClass.getId());
        showtarnema.putExtra("Url",tranemClass.getUrl());
        showtarnema.putExtra("Words",tranemClass.getWord());
        startActivity(showtarnema);
    }
}
