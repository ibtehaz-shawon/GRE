package com.zerothtech.www.gre;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        //ToDo
        //AppRater ar = new AppRater();
        //ar.app_launched(this);


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
        else if(id == R.id.action_reset)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You are going to reset the word counter and delete all your favourite words. Click YES to confirm" +
                    '\n'+"This process is irreversable!")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            SharedPreferences preferences = getSharedPreferences("number", 0);
                            boolean flag1 = preferences.edit().remove("star2").commit();
                            boolean flag2 = preferences.edit().remove("index").commit();
                            boolean flag3 = preferences.edit().remove("quizCounter").commit();
                            boolean flag4 = preferences.edit().remove("correctVal").commit();

                            if (flag1 && flag2 && flag3 && flag4)
                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
            builder.create();
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
