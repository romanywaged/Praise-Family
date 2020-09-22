package com.example.romany.activities;
import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.romany.R;
import com.example.romany.model.CommonFunctions;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.io.FileOutputStream;


public class ShowTarnema extends AppCompatActivity {
    public TextView words;
    public WebView vedio;
    public String url;
    public String name;
    public String word;
    private static final int code = 1000;
    private CommonFunctions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tarnema);
        functions = new CommonFunctions();
        initiView();

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //Define Design
    private void initiView() {
        vedio = (WebView) findViewById(R.id.vedio);
        name = getIntent().getExtras().getString("Name");
        String id = getIntent().getExtras().getString("Id");
        url = getIntent().getExtras().getString("Url");
        word = getIntent().getExtras().getString("Words");
        words = (TextView) findViewById(R.id.words);
        getSupportActionBar().setTitle(name);
        words.setText(word);
    }


    //Create Menue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.watch, menu);
        return true;
    }


    //Menue Options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.watch:
                CheckConnection();
                return true;
            case R.id.copy:
                CopyToClibBoard();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //If Request Result
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CreatePDF();
                } else {
                    Toast.makeText(ShowTarnema.this, "Permission denied", Toast.LENGTH_LONG).show();
                }
        }
    }*/


    //Copy Words To ClibBoard
    private void CopyToClibBoard() {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied!", words.getText().toString());
        manager.setPrimaryClip(clipData);
        Toast.makeText(this, "Copied", Toast.LENGTH_LONG).show();
    }


    //Chck Permissions
    private void ExportFile() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, code);
            } else {
                CreatePDF();
            }
        } else {
            CreatePDF();
        }
    }


    //Create Word
    private void saveWord() {
        File textfile = new File(Environment.getExternalStorageDirectory() + "/" + name + ".docx");
        try {
            FileOutputStream fos = new FileOutputStream(textfile);
            fos.write(word.getBytes());
            fos.close();
            Toast.makeText(ShowTarnema.this, name + "is saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(ShowTarnema.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void CreatePDF()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            PdfDocument pdfDocument=new PdfDocument();
            PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(300,600,1).create();
            PdfDocument.Page page=pdfDocument.startPage(pageInfo);
            Paint myPaint=new Paint();
            int x=10,y=25;
            page.getCanvas().drawText(word,x,y,myPaint);
            pdfDocument.finishPage(page);

            String MyFilePath=Environment.getExternalStorageDirectory().getPath()+name+".pdf";
            File myfile=new File(MyFilePath);

            try {
                pdfDocument.writeTo(new FileOutputStream(myfile));
                Toast.makeText(this,"Yes  ",Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            pdfDocument.close();
        }
        else
        {
            Toast.makeText(this,"Your Device Not Allow to create file ",Toast.LENGTH_LONG).show();
        }

    }







  //Check Internet Status
    private void CheckConnection()
    {
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if (null!=activeNetwork)
        {
            if (activeNetwork.getType()==ConnectivityManager.TYPE_WIFI)
            {
                functions.ShowMessage(getApplicationContext(),"WIFI ENABLED");
                vedio.loadUrl(url);
            }

            if (activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE)
            {
                functions.ShowMessage(getApplicationContext(),"DATA ENABLED");
                vedio.loadUrl(url);
            }
        }
        else
        {
            functions.ShowMessage(getApplicationContext(),"No Internet");
            vedio.setVisibility(View.INVISIBLE);
        }
    }
}

