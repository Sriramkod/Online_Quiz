package com.example.ksriram.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    Button change;
    Button profile;
    Button doubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        change = (Button)findViewById(R.id.button2);
        doubt = (Button)findViewById(R.id.button);
        profile = (Button)findViewById(R.id.button1);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Settings.this,"Change your password",Toast.LENGTH_LONG).show();
            }
        });
        doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Settings.this,"Ask a doubt",Toast.LENGTH_LONG).show();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Settings.this,"View Your profile",Toast.LENGTH_LONG).show();
            }
        });
    }

}
