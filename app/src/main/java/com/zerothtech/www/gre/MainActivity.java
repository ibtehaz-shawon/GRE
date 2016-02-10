package com.zerothtech.www.gre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button words = (Button)findViewById(R.id.words);
        Button starred = (Button)findViewById(R.id.starred);
        Button quiz = (Button)findViewById(R.id.quiz);

        getSupportActionBar().setTitle("GRE");

        final Intent i1 = new Intent(this, Card_Show.class);
        final Intent i2, i3;
        i2 = new Intent(this, Favourite_Words.class);
        i3 = new Intent(this, Quiz.class);

        final int number_of_favourite = favouriteText();

        AppRater ar = new AppRater();
        ar.app_launched(this);


        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });

        starred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number_of_favourite == 0)
                {
                    String text = "Damn it! You have not marked any word FAVOURITE";
                    Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(i2);
                }
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i3);
            }
        });
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


    //    needed to be ediited later
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //    needed to be edited later
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent i = new Intent(this, AboutUs.class);
        Intent i2 = new Intent(this, Statistics.class);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_stat) {
            startActivity(i2);
        }
        else if(id == R.id.action_about)
        {
            startActivity(i);
        }
        else if(id == R.id.action_rate)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.zerothtech.www.gre")));
            Toast.makeText(getApplicationContext(),"Thank you for your rating", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
