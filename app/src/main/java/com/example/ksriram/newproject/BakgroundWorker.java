package com.example.ksriram.newproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

public class BakgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
public String email;
    BakgroundWorker (Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];

        String login_url = "https://f88a1c9c.ngrok.io/chintu/UserLogin.php";

        if(type.equals("login")){
            try {
                 email = voids[1];
                /*SharedPreferences sco = getSharedPreferences("Score",MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sco.edit();
                prefEditor.getInt("Score",i);
                prefEditor.commit();*/
               /* SharedPreferences sco  = PreferenceManager.getDefaultSharedPreferences(context);
               int  pass  =  sco.getInt("Score",1);
               String password = Integer.toString(pass);*/
               //  String password = SharedPreferences.
                String password = voids[2];

                URL url = new URL(login_url);
                HttpURLConnection  httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream =  httpURLConnection.getInputStream();
                BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "Checking","Please Wait....", true);

    }

    @Override
    protected void onPostExecute(String result) {
         super.onPostExecute(result);
         progressDialog.dismiss();
         if(result.equals("login sucess"))
         {
             Intent intent = new Intent(context, QuizActivity.class);

             Bundle extras = ((Activity) context).getIntent().getExtras();
             String acess = extras.getString("acess");
             intent.putExtra("acess",acess);
             intent.putExtra("id",email);
             context.startActivity(intent);
             ((Activity)context).finish();

         }
         else
         {
             Toast.makeText(context,"Login failed",Toast.LENGTH_LONG).show();
             return;
         }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
