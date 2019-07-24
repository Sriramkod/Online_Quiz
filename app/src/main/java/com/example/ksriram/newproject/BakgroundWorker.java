package com.example.ksriram.newproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class BakgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    BakgroundWorker (Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];

        String login_url = "https://21ca84f7.ngrok.io/chintu/UserLogin.php";

        if(type.equals("login")){
            try {
                String email = voids[1];
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
       /* alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");
*/
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Checking","Wait....", true);
//progressDialog.show();
//progressDialog.setMessage("loading");
    }

    @Override
    protected void onPostExecute(String result) {
       /*alertDialog.setMessage(result);
       alertDialog.show();*/

         super.onPostExecute(result);

         progressDialog.dismiss();
         if(result.equals("Login sucess"))
         {
             Intent intent = new Intent(context, QuizActivity.class);
             Bundle extras = ((Activity) context).getIntent().getExtras();
             String acess = extras.getString("acess");
             intent.putExtra("acess",acess);
             context.startActivity(intent);
             ((Activity)context).finish();

         }
         else
         {
             Intent intent = new Intent(context, sigin.class);
             //Intent mIntent = getIntent();
            // String key = intent.getStringExtra("acess");
            // intent.putExtra("acess",key);
             context.startActivity(intent);
             ((Activity)context).finish();
             Toast.makeText(context,"Login failed",Toast.LENGTH_LONG).show();
         }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
