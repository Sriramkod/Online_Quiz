package com.example.ksriram.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        Button startButton = (Button)findViewById(R.id.button);
        final EditText editText = (EditText)findViewById(R.id.acess);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.acess);
                if(text.equals(123)){
                    Toast.makeText(HomeActivity.this, "Enter the acess key", Toast.LENGTH_LONG).show();
                }
                else {
                    String acessKey = editText.getText().toString();
                    Intent intent = new Intent(HomeActivity.this, QuizActivity.class);

                    intent.putExtra("acessKey", acessKey);
                    startActivity(intent);

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
