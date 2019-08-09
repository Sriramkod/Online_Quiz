package com.example.ksriram.newproject;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.content.SharedPreferences;

public class QuizActivity extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private CountDownTimer countDownTimer;
    private long timerLeftInMillis;
    ArrayList<Integer> milk;
    private TextView quizQuestion;
    public int cc=0;
    boolean clicked=false;
    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private RadioButton def;

    int[] a = new int[100];
    //public static int i=0;
    //public int k=0;
    public String i = "0";
    public String k = "0";
   /* String s = "0";
    s = String.valueOf(Integer.parseInt(s) + 1);*/
    private int currentQuizQuestion;
    private int quizCount;
public int qeez=1;
    private QuizWrapper firstQuestion;
    private List<QuizWrapper> parsedObject;

    Button submit;
   public Button nextButton;
    TextView dis;
    TextView timer;
    TextView timer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        milk = new ArrayList<>();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        submit=(Button)findViewById(R.id.button2);
        timer = (TextView)findViewById(R.id.timer); timer1 = (TextView)findViewById(R.id.timer1);
        quizQuestion = (TextView)findViewById(R.id.quiz_question);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        optionOne = (RadioButton)findViewById(R.id.radio0);
        optionTwo = (RadioButton)findViewById(R.id.radio1);
        optionThree = (RadioButton)findViewById(R.id.radio2);
        optionFour = (RadioButton)findViewById(R.id.radio3);
        def = (RadioButton)findViewById(R.id.radiod);
        def.setButtonDrawable(android.R.color.transparent);
        def.setPadding(31, 0, 0, 0);
        def.setChecked(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent in = new Intent(QuizActivity.this,check.class);

                Intent mIntent = getIntent();
                String email = mIntent.getStringExtra("id");
                in.putExtra("im",i);
                GlobalClass  globalClass = (GlobalClass)getApplicationContext();
                globalClass.setScore(i);
                String type = "insert";
               // Toast.makeText(QuizActivity.this," "+sc,Toast.LENGTH_LONG).show();
                BackgroundTask backgroundTask = new BackgroundTask(QuizActivity.this);
               // i="9";
                backgroundTask.execute(type,email,i);
                //startActivity(in);
                //finish();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                builder.setTitle("Online Quiz");
                builder.setMessage("Your Quiz didn't end,Do you like to submit...")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int kk) {

                                        /*Intent in = new Intent(QuizActivity.this,check.class);

                                        in.putExtra("im",k);
                                        startActivity(in);*/
                                Intent mIntent = getIntent();
                                String email = mIntent.getStringExtra("id");
                                mIntent.putExtra("im",i);

                                //String sc = Integer.toString(i);
                                GlobalClass  globalClass = (GlobalClass)getApplicationContext();
                                globalClass.setScore(i);
                                String type = "insert";
                                BackgroundTask backgroundTask = new BackgroundTask(QuizActivity.this);
                                backgroundTask.execute(type,email,i);
                            }
                        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

         nextButton = (Button)findViewById(R.id.nextquiz);

        AsyncJsonObject asyncObject = new AsyncJsonObject();
        asyncObject.execute("");

//timer1.setText(qeez+"/"+quizCount);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 clicked=true;
                int radioSelected = radioGroup.getCheckedRadioButtonId();
                int userSelection = getSelectedAnswer(radioSelected);

                int correctAnswerForQuestion = firstQuestion.getCorrectAnswer();

                if(!(optionOne.isChecked()||optionTwo.isChecked()||optionThree.isChecked()||optionFour.isChecked()||def.isChecked()))
                {
                    Toast.makeText(QuizActivity.this, "Select any one of the option", Toast.LENGTH_LONG).show();
                }
                else{

                    if(userSelection==correctAnswerForQuestion) {
                        //Toast.makeText(QuizActivity.this, " correct ", Toast.LENGTH_LONG).show();
                        //i++;
                        //k++;
                        i = String.valueOf(Integer.parseInt(i) + 1);
                        Toast.makeText(QuizActivity.this,"Score "+i,Toast.LENGTH_LONG);
                    }
                    def.setButtonDrawable(android.R.color.transparent);
                    def.setPadding(31, 0, 0, 0);
                    def.setChecked(true);
                    currentQuizQuestion++;

                    if(currentQuizQuestion >= quizCount){

                            optionOne.setVisibility(View.INVISIBLE);
                        optionTwo.setVisibility(View.INVISIBLE);
                        optionThree.setVisibility(View.INVISIBLE);
                        optionFour.setVisibility(View.INVISIBLE);
                        quizQuestion.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                        builder.setTitle("Online Quiz");
                        builder.setMessage("End of questions do you like to submit")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int kk) {

                                        /*Intent in = new Intent(QuizActivity.this,check.class);

                                        in.putExtra("im",k);
                                        startActivity(in);*/
                                        Intent mIntent = getIntent();
                                        String email = mIntent.getStringExtra("id");
                                        mIntent.putExtra("im",i);

                                        //String sc = Integer.toString(i);
                                        GlobalClass  globalClass = (GlobalClass)getApplicationContext();
                                        globalClass.setScore(i);
                                        String type = "insert";
                                        BackgroundTask backgroundTask = new BackgroundTask(QuizActivity.this);
                                        backgroundTask.execute(type,email,i);
                                    }
                                }).setNegativeButton("cancel",null);
                        AlertDialog alert = builder.create();
                        alert.show();

                        Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();

                        return;
                    }
                    else{

                        firstQuestion = parsedObject.get(currentQuizQuestion);
                        quizQuestion.setText(firstQuestion.getQuestion());
                        String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                        //uncheckedRadioButton();
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                        qeez++;

                        //timerLeftInMillis = COUNTDOWN_IN_MILLIS;
                        timer1.setText(currentQuizQuestion+1+"/"+quizCount);
                    }

                }
                clicked=false;
            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncJsonObject extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            //HttpPost httpPost = new HttpPost("https://learnfriendly.000webhostapp.com/json.php");
            //HttpPost httpPost = new HttpPost("https://21ca84f7.ngrok.io/json.php");
            Intent mIntent = getIntent();
            String key = mIntent.getStringExtra("acess");
            HttpPost httpPost = new HttpPost("https://f88a1c9c.ngrok.io/json.php");
           // HttpPost httpPost = new HttpPost("https://"+key+".ngrok.io/json.php");
            String jsonResult = "";

            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(QuizActivity.this, "Downloading Quiz","Wait....", true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            System.out.println("Resulted Value: " + result);
            parsedObject = returnParsedJsonObject(result);

            //Iterator<Object> keys = parsedObject.keys();
            if(parsedObject== null){
                Intent checkIntent = new Intent(QuizActivity.this, MainActivity.class);
                checkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                checkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(checkIntent);
            }


            quizCount = parsedObject.size();
            firstQuestion = parsedObject.get(0);

            quizQuestion.setText(firstQuestion.getQuestion());
            String[] possibleAnswers = firstQuestion.getAnswers().split(",");
            optionOne.setText(possibleAnswers[0]);
            optionTwo.setText(possibleAnswers[1]);
            optionThree.setText(possibleAnswers[2]);
            optionFour.setText(possibleAnswers[3]);
            timer1.setText(currentQuizQuestion+1+"/"+quizCount);

        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private List<QuizWrapper> returnParsedJsonObject(String result){

        List<QuizWrapper> jsonObject = new ArrayList<QuizWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            System.out.println("Online quiz " + resultObject.toString());

            jsonArray = resultObject.optJSONArray("user_info");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);

                String id="$",question="$",answerOptions="$";
                int correctAnswer;
                id = jsonChildNode.getString("id");
                question = jsonChildNode.getString("Question");
                answerOptions = jsonChildNode.getString("optionD");
                correctAnswer = jsonChildNode.getInt("Answer");
                    newItemObject = new QuizWrapper(id, question, answerOptions, correctAnswer);
                    jsonObject.add(newItemObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private int getSelectedAnswer(int radioSelected){

        int answerSelected = 0;
        if(radioSelected == R.id.radiod){
            answerSelected = 0;
        }

        if(radioSelected == R.id.radio0){
            answerSelected = 1;
        }
        if(radioSelected == R.id.radio1){
            answerSelected = 2;
        }
        if(radioSelected == R.id.radio2){
            answerSelected = 3;
        }
        if(radioSelected == R.id.radio3){
            answerSelected = 4;
        }
        return answerSelected;
    }
    private void uncheckedRadioButton(){
        optionOne.setChecked(false);
        optionTwo.setChecked(false);
        optionThree.setChecked(false);
        optionFour.setChecked(false);
    }
public void onBackPressed(){
    AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
    builder.setTitle("Exit Online Quiz");
    builder.setMessage("Do you want to leave")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    QuizActivity.super.onBackPressed();
                }
            }).setNegativeButton("cancel",null);
    AlertDialog alert = builder.create();
    alert.show();
}

}