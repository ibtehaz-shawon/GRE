package com.zerothtech.www.gre;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Card_Show extends AppCompatActivity {


    TextView question, answerView;
    ImageButton check, next, previous;

    boolean isBackVisible = false; // Boolean variable to check if the back image is visible currently

    private static String []word,answer;
    private static int wordC ,answerC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__show);
        getSupportActionBar().setTitle("Words & Synonyms");

        question = (TextView)findViewById(R.id.textviewSyn);
        answerView = (TextView)findViewById(R.id.TextViewAnswer);

        check = (ImageButton) findViewById(R.id.check);
        next = (ImageButton) findViewById(R.id.nextSyn);
        previous = (ImageButton) findViewById(R.id.prev);




        final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_right_out);

        final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_left_in);
        loadArray();
        wordC = 0;
        answerC = 0;

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

        if(temp == -1)
        {
            wordC = answerC = 0;
        }else {
            wordC = answerC = temp;
        }



        visibleFront();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBackVisible) {
                    setRightOut.setTarget(question);
                    setLeftIn.setTarget(answerView);
                    setRightOut.start();
                    setLeftIn.start();
                    visibleBack();
                    //wordC++;
                    saveIndex();
                    isBackVisible = true;
                } else {
                    setRightOut.setTarget(answerView);
                    setLeftIn.setTarget(question);
                    setRightOut.start();

                    setLeftIn.start();
                    isBackVisible = false;

                    //if check is being hit second time, thn I need to change the counter of the word and answer
                    //answerC++;
                    //wordC++;

                    if(wordC == 1400)
                    {
                        wordC = 0;
                        answerC = 0;
                    }
                    else
                    {
                        wordC = (wordC + 1) % 1400;
                        answerC = (answerC + 1) % 1400;
                    }
                    visibleFront();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBackVisible) {
                    if(wordC == 1400)
                    {
                        wordC = 0;
                        answerC = 0;
                    }
                    else
                    {
                        wordC = (wordC + 1) % 1400;
                        answerC = (answerC + 1) % 1400;
                    }

                    visibleFront();
                    saveIndex();
                }
                else
                {
                    setRightOut.setTarget(answerView);
                    setLeftIn.setTarget(question);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;
                    if(wordC == 1400)
                    {
                        wordC = 0;
                        answerC = 0;
                    }
                    else
                    {
                        wordC = (wordC + 1) % 1400;
                        answerC = (answerC + 1) % 1400;
                    }
                    visibleFront();
                    saveIndex();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBackVisible) {

                    if(wordC > 0) {
                        wordC = (wordC - 1) % 1400;
                        answerC = (answerC - 1) % 1400;
                    }
                    else
                    {
                        wordC = 0;
                        answerC = 0;
                    }
                    visibleFront();
                    saveIndex();
                }
                else
                {
                    setRightOut.setTarget(answerView);
                    setLeftIn.setTarget(question);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;

                    if(wordC > 0) {
                        wordC = (wordC - 1) % 1400;
                        answerC = (answerC - 1) % 1400;
                    }
                    else
                    {
                        wordC = 0;
                        answerC = 0;
                    }
                    visibleFront();
                    saveIndex();
                }
            }
        });

    }

    private void visibleFront()
    {
        final String post = "Word : "+ word[wordC];
        question.setText(post);
    }


    private void visibleBack()
    {
        final String post = "Word : "+ word[wordC];
        final String result = "Synonym : "+answer[answerC];
        answerView.setText(post + '\n' + result);

    }

    private boolean saveIndex()
    {
        SharedPreferences settings;
        settings = getSharedPreferences("number", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("index", wordC);

        boolean bool = editor.commit();
        if(bool)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean readAndWrite()
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

        String value = "";
        if(temp.length() == 0)
        {
            value = wordC + "";
        }
        else
        {
            value =   temp + "," + wordC;
        }

        settings = getSharedPreferences("number", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("star2", value);

        // Commit the edits!
        boolean bool = editor.commit();
        if(bool)
        {
            return true;
        }
        else
        {
            return false;
        }

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
                        answer[arrayC] = jsonObj.getString("Synonym");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_show, menu);
        return true;
    }


    //    needed to be edited later
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_fav)
        {
            boolean flag = readAndWrite();
            if(flag)
                Toast.makeText(getApplicationContext(), "YEAH!!\nSaved Word : "+word[wordC], Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "DAMN!! This was not suppose to happen!\nGive it a another shot!!",
                        Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }
}
