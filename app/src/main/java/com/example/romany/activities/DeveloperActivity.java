package com.example.romany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.romany.R;

public class DeveloperActivity extends AppCompatActivity {

    TextView romany_linkedIn,romany_facebook,ramy_LinkedIn,ramy_Facebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        romany_linkedIn=findViewById(R.id.roma_Linked);
        romany_facebook=findViewById(R.id.roma_face);
        ramy_LinkedIn=findViewById(R.id.Linked_Ramy);
        ramy_Facebook=findViewById(R.id.Face_Ramy);

        ramy_LinkedIn.setMovementMethod(LinkMovementMethod.getInstance());
        ramy_Facebook.setMovementMethod(LinkMovementMethod.getInstance());



        romany_facebook.setMovementMethod(LinkMovementMethod.getInstance());
        romany_linkedIn.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DeveloperActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
