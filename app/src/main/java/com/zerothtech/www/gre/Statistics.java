package com.zerothtech.www.gre;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getSupportActionBar().setTitle("Statistics");

        TextView stat_word = (TextView)findViewById(R.id.stat_word);
        TextView stat_fav = (TextView) findViewById(R.id.stat_fav);
        TextView stat_success = (TextView) findViewById(R.id.stat_quizSuccess);

        int wordRead = word_read();
        String line1 = "";

        if(wordRead == -1)
        {
            line1 = "You did not read any word yet. Give it a try";
        }
        else
        {
            line1 = "Congratulations! You have read "+wordRead+" so far.\nKeep going!!";
        }

        stat_word.setText(line1);

        int fav_text = favouriteText();
        String line2 = "";


        if(fav_text == 0)
        {
            line2 = "You have not marked any your favourites";
        }
        else
        {
            line2 = "You have marked "+fav_text+" words your favorites";
        }

        stat_fav.setText(line2);

        int correct = getCorrect(this);
        int quizCounter = getQuizCounter(this);
        double success = 0;
        if(quizCounter != 0)
        {
            success = (correct / (quizCounter *10.0))*100;
        }

        String value_success = new DecimalFormat("##.##").format(success);

        String line3 = "";
        if(quizCounter > 0)
        {
            if(success > 50.00)
            {
                line3 = "You have successfully answered "+correct+" out of "+quizCounter*10+" quiz questions! Good job!";
                line3 = line3 + "(" +value_success+"%).";
            }
            else
            {
                line3 = "You have answered "+correct+" out of "+quizCounter*10+" quiz questions! Keep working!";
                line3 = line3 + "(" +value_success+"%).";
            }

        }
        else
        {
            line3 = "Oh No!! We are not ready with the stats yet! Why don't you take some quizes. We will be ready by then.";
        }

        stat_success.setText(line3);
    }

    private int word_read()
    {
        SharedPreferences settings;
        int temp = -1;
        try {
            settings = this.getSharedPreferences("number", 0);
            temp = settings.getInt("index", -1);
        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }
        return temp;
    }

    private int favouriteText()
    {
        SharedPreferences settings;
        String temp = "";
        try {
            settings = getSharedPreferences("number", 0);
            temp = settings.getString("star2", "");
        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }
        return temp.length();
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
