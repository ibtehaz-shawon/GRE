package com.zerothtech.www.gre;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
            line1 = "You did not check any word yet. Why don't you try some of those?";
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
            line2 = "You have not marked any word your favourites";
        }
        else
        {
            line2 = "You have marked "+fav_text+" words your favorites";
        }

        stat_fav.setText(line2);

        int correct = getCorrect(this);
        int quizCounter = getQuizCounter(this);
        int success = (int)(correct / (quizCounter *10)*100);

        String line3 = "";
        if(quizCounter > 0)
        {
            line3 = "You have successfully answered "+correct+" out of "+quizCounter*10+" quiz questions! Good job!";
            line3 = line3 + "(" +success+"%).";
        }
        else
        {
            line3 = "You have not participated in any quizes yet! Try some, your stats will be ready by then!!";
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
