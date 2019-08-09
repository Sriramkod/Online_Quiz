package com.example.ksriram.newproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class sucess extends AppCompatActivity {

    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        textview = (TextView) findViewById(R.id.textView8);
        Intent mIntent = getIntent();
        String email = mIntent.getStringExtra("id");

        textview.setText("Hello "+email);
    }
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(sucess.this);
        builder.setTitle("Exit Online Quiz");
        builder.setMessage("Do you want to leave")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent checkIntent = new Intent(sucess.this, HomeActivity.class);
                        checkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(checkIntent);
                        finish();
                        sucess.super.onBackPressed();
                    }
                }).setNegativeButton("cancel",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
