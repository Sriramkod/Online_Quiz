package com.example.ksriram.newproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class check extends AppCompatActivity {
TextView textview;
ArrayList<Integer> quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
//        Bundle bundle = getIntent().getExtras();
//        quiz = (ArrayList<Integer>) bundle.getSerializable("answers");
        textview = (TextView) findViewById(R.id.textView2);
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("im", 0);
        textview.setText(" "+intValue);


    }
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(check.this);
        builder.setTitle("Exit Online Quiz");
        builder.setMessage("Do you want to leave")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent checkIntent = new Intent(check.this, HomeActivity.class);
                        checkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(checkIntent);
                        finish();
                        check.super.onBackPressed();
                    }
                }).setNegativeButton("cancel",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}