package com.zerothtech.www.gre;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        int correct = Integer.parseInt(getIntent().getStringExtra("correct"));
        getSupportActionBar().setTitle("Quiz Stat");
        TextView correctAns = (TextView)findViewById(R.id.result_scoreBoard);
        TextView totalResult = (TextView)findViewById(R.id.result_totalBoard);
        TextView totalQuiz = (TextView)findViewById(R.id.result_totalQuiz);

        String line1 = "You have corrected "+correct+" answers";
        correctAns.setText(line1);

        int totalCorrect = getCorrect(this);
        int totalQuizTaken = getQuizCounter(this);

        double percentage = (totalCorrect / (totalQuizTaken * 10.0)) * 100;
        String value_success = new DecimalFormat("##.##").format(percentage);

        String line2 = "";
        if(percentage == 0)
        {
            line2 = "Success Percentage "+value_success+"%";
        }
        else
        {
            line2 = "Success Percentage "+value_success+"%";
        }

        totalResult.setText(line2);

        String line3 = "Total Quiz Taken "+totalQuizTaken;
        totalQuiz.setText(line3);

    }

    private int  getCorrect(Activity act) {
        SharedPreferences settings;
        int temp = 0;

        try {
            settings = act.getSharedPreferences("number", 0);
            temp = settings.getInt("correctVal", 0);
        } catch (NullPointerException np) {
            np.printStackTrace();
        }

        return temp;
    }


    private int getQuizCounter(Activity act)
    {
        SharedPreferences settings;
        int quizCounter = 0;

        try {
            settings = act.getSharedPreferences("number", 0);
            quizCounter = settings.getInt("quizCounter", 0);
        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }

        return quizCounter;
    }


}
