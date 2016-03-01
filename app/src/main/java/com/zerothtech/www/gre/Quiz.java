package com.zerothtech.www.gre;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Quiz extends AppCompatActivity {

    int []question,option;
    private static String []word,answer;
    TextView title, post, scoreCard;
    RadioButton one,two, three, four;
    ImageButton next;
    int correct = 0;
    int index = 0;
    int counter = 0;
    RadioGroup rg;
    String correctAns = "";


    private Handler mhandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);

            int quizC = saveQuiz(this, 0);
            getSupportActionBar().setTitle("Quiz "+quizC);

            question = new int[10];
            option = new int[60];

            //arrayMake();
            getQuestion();
            getOptions();
            loadArray();


            title = (TextView) findViewById(R.id.quiz_title);
            post = (TextView) findViewById(R.id.quiz_question);
            scoreCard = (TextView)findViewById(R.id.quiz_scoreBoard);
            one = (RadioButton) findViewById(R.id.one);
            two = (RadioButton) findViewById(R.id.two);
            three = (RadioButton) findViewById(R.id.three);
            four = (RadioButton) findViewById(R.id.four);
            next = (ImageButton) findViewById(R.id.quiz_next);
            rg = (RadioGroup) findViewById(R.id.quiz_radioGroup);

            index = 0;
            correct = 0;
            scoreCard.setText("Correct : "+correct);

            final Intent i = new Intent(this, Result.class);

            postQuestion();

            postOptions();

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        RadioButton rbb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                        String text = rbb.getText().toString();
                        rbb.setChecked(false);

                        if (!rbb.getText().toString().equalsIgnoreCase(null)) {

                            checkCorrect(text, true);
                        } else {
                            checkCorrect(null, false);
                        }
                    } catch (Exception e) {
                        //what to do.
                    }

                    if (index > 9) {
                        //Game Over MOnkey
                        //Toast
                        Toast.makeText(getApplicationContext(), "AND! Quiz is finished !!", Toast.LENGTH_SHORT).show();
                        mhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                i.putExtra("correct", correct + "");
                                startActivity(i);
                            }
                        }, 2000);
                    }
                }
            });
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private boolean  saveCorrect(Activity act, int correct) {
        SharedPreferences settings;
        int temp = 0;

        try {
            settings = act.getSharedPreferences("number", 0);
            temp = settings.getInt("correctVal", 0);
        } catch (NullPointerException np) {
            np.printStackTrace();
        }

        temp += correct;
        settings = act.getSharedPreferences("number", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("correctVal", temp);

        boolean bool = editor.commit();
        if (bool) {
            return true;
        } else {
            return false;
        }
    }


    private int saveQuiz(Activity act, int quizCounter)
    {
        SharedPreferences settings;

        try {
            settings = act.getSharedPreferences("number", 0);
            quizCounter = settings.getInt("quizCounter", 0);
            quizCounter++;
        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }


        settings = act.getSharedPreferences("number", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("quizCounter", quizCounter);
        editor.commit();

        return quizCounter;

    }

    private void checkCorrect(String text, boolean val)
    {
        if(val)
        {
            if(text.equalsIgnoreCase(correctAns))
            {
                Toast.makeText(getApplicationContext(), "YEAH!! Correct Answer", Toast.LENGTH_SHORT).show();
                correct++;
                correctAnswer();
                scoreCard.setText("Correct : "+correct);

                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        postAction();
                    }
                }, 1000);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Oh no!! Wrong Answer", Toast.LENGTH_SHORT).show();
                wrongAnswer();
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        postAction();
                    }
                }, 2000);
            }
        }
    }

    private void postAction()
    {

        if(index <= 9) {
            one.setChecked(false);
            two.setChecked(false);
            three.setChecked(false);
            four.setChecked(false);

            postQuestion();
            postOptions();
        }
        else
        {
            saveCorrect(this, correct);
        }
    }




    private void correctAnswer()
    {
        if(one.getText().toString().equalsIgnoreCase(correctAns))
        {
            one.setTextColor(Color.parseColor("#0000cc"));

        }else if(two.getText().toString().equalsIgnoreCase(correctAns))
        {
            two.setTextColor(Color.parseColor("#0000cc"));
        }else if(three.getText().toString().equalsIgnoreCase(correctAns))
        {
            three.setTextColor(Color.parseColor("#0000cc"));
        }else if(four.getText().toString().equalsIgnoreCase(correctAns))
        {
            four.setTextColor(Color.parseColor("#0000cc"));
        }
    }

    private void wrongAnswer()
    {
        if(one.getText().toString().equalsIgnoreCase(correctAns))
        {
            one.setTextColor(Color.parseColor("#FF2100"));

        }else if(two.getText().toString().equalsIgnoreCase(correctAns))
        {
            two.setTextColor(Color.parseColor("#FF2100"));
        }else if(three.getText().toString().equalsIgnoreCase(correctAns))
        {
            three.setTextColor(Color.parseColor("#FF2100"));
        }else if(four.getText().toString().equalsIgnoreCase(correctAns))
        {
            four.setTextColor(Color.parseColor("#FF2100"));
        }
    }

    //error
    private void postQuestion()
    {
        one.setTextColor(Color.parseColor("#000000"));
        two.setTextColor(Color.parseColor("#000000"));
        three.setTextColor(Color.parseColor("#000000"));
        four.setTextColor(Color.parseColor("#000000"));

        String line1 = " out of 10";
        String ques = "";
        ques = word[question[index]]; //MOTHER OF error
        correctAns = answer[question[index]];
        index++;
        title.setText(index+ line1);


        post.setText(ques);
    }


    private void postOptions()
    {
        int op1;
        op1 = randInt(0,3);

        if(op1 == 0)
        {
            one.setText(correctAns);
        }
        else {
            one.setText(answer[option[(counter++)%60]]);
        }

        if(op1 == 1)
        {
            two.setText(correctAns);
        }
        else {
            two.setText(answer[option[(counter++)%60]]);
        }

        if(op1 == 2)
        {
            three.setText(correctAns);
        }
        else {
            three.setText(answer[option[(counter++)%60]]);
        }

        if(op1 == 3)
        {
            four.setText(correctAns);
        }
        else {
            four.setText(answer[option[(counter++)%60]]);
        }
    }



    private void getQuestion()
    {
        int min = 0;
        int max = 140;
        for(int i = 0; i < 10; )
        {
            int val = randInt(min, max);
            boolean flag = false;

            for(int j = 0; j < i; j++)
            {
                if(question[j] == val)
                {
                    flag = true;
                    break;
                }
            }
            if(!flag)
            {
                question[i] = val; //value is unique
                i++;
                min = max;
                max = max + 140;
            }


        }
    }

    private void getOptions()
    {
        for (int i = 0; i < option.length; i++)
        {
            option[i] = randInt(0, 1400);
        }
    }


    private int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1)+min;
        return randomNum;
    }


    private void loadArray()
    {
        String filename = "wordlist";
        String ext = ".txt";
        JSONArray jsonArray = null;

        // JSONArray has four JSONObject
        word = new String[1500];
        answer = new String[1500];
        int arrayC = 0;

        for(int counter = 1; counter <= 15; counter++)
        {
            // Try to parse JSON
            try {
                // Creating JSONObject from String

                String jsonString = loadJson(filename+counter+ext); //load the JSON file and send it to JSONSTring

                JSONObject jsonObjMain = new JSONObject(jsonString);

                // Creating JSONArray from JSONObject
                if(counter != 2) {
                    int tempValue = counter % 5;
                    if (tempValue == 0) tempValue = 5;
                    jsonArray = jsonObjMain.getJSONArray("WordList" + tempValue);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        word[arrayC] = jsonObj.getString("Word");
                        String value = jsonObj.getString("Synonym");
                        value = separateComma(value);
                        answer[arrayC] = value;
                        arrayC++;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String loadJson(String file)
    {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(file)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();

    }

    private String separateComma(String value)
    {
        String returnVal = "";

        String [] array = value.split(";");
        for(int i = 0; i < array.length; i++)
        {
            returnVal = returnVal + array[i] + " ";
        }

        return returnVal;
    }

}

