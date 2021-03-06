package com.example.ksriram.newproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity{
    boolean connected = false;
    EditText editText;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    Spinner spinner;

    String SpinnerValue;
    Button GOTO;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);


        Button startButton = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.acess);

        //boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                //EditText text = (EditText)findViewById(R.id.acess);
               String edit = editText.getText().toString();
                if(connected){
                    Intent intent = new Intent(HomeActivity.this, sigin.class);
                    intent.putExtra("acess",edit);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(HomeActivity.this, "Please check your INTERNET connection ", Toast.LENGTH_LONG).show();
                }

                /*Intent intent = new Intent(HomeActivity.this, QuizActivity.class);

                startActivity(intent);*/

            }

        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
           Intent intent = new Intent(HomeActivity.this,Settings.class);
           startActivity(intent);
            return true;
        }
        if(item.getItemId()  ==  R.id.action_about)
        {
            Intent intent = new Intent(HomeActivity.this,about.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
