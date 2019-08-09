package com.example.ksriram.newproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.os.Bundle;
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

public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    public String email;
    public String score;
    BackgroundTask(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];

        String login_url = "https://f88a1c9c.ngrok.io/chintu/score.php";

        if(type.equals("insert")){
            try {
                Bundle extras = ((Activity) context).getIntent().getExtras();
               // String acess = extras.getString("im");
                email = voids[1];

                //score = voids[2];

    //            GlobalClass  globalClass = (GlobalClass)Context.getApplicationContext();
                //globalClass.getScore(i);
                GlobalClass globalClass = (GlobalClass) ((Activity) context).getApplicationContext();
     score =  globalClass.getScore();
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("score","UTF-8")+"="+URLEncoder.encode(score,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream =  httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
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
       /* SharedPreferences sharedPreferences = context.getSharedPreferences("Test_score",Context.MODE_PRIVATE);
        int  pass;
        pass = sharedPreferences.getInt("Score",100);
         score = Integer.toString(pass);
*/
        progressDialog = ProgressDialog.show(context, "Uploading your score","Please Wait....", true);

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if(result.equals("inserted"))
        {
            Toast.makeText(context,"Test Submitted sucessfully......",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context,sucess.class);

            Bundle extras = ((Activity) context).getIntent().getExtras();
            String acess = extras.getString("acess");
            intent.putExtra("acess",acess);
            intent.putExtra("id",email);
            context.startActivity(intent);
            ((Activity)context).finish();

        }
        else
        {
            Toast.makeText(context,"Not inserted",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context,HomeActivity.class);
            context.startActivity(intent);
            return;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

