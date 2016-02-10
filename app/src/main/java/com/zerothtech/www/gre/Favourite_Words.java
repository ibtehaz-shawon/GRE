package com.zerothtech.www.gre;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Favourite_Words extends AppCompatActivity {


    TextView totalWords, synonym;
    LinearLayout linear;
    Button btn_next;
    FrameLayout frameLayout;
    private boolean flag = false;
    private static String []word,answer;
    private int minus = 0;
    boolean isBackVisible = false; // Boolean variable to check if the back image is visible currently
    int[] array;
    AnimatorSet setRightOut, setLeftIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite__words);
        btn_next = (Button)findViewById(R.id.favourite_word_btn);

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_right_out);

        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.flight_left_in);


        synonym = (TextView)findViewById(R.id.favourite_word_synonym);

        totalWords = (TextView) findViewById(R.id.favourite_word_tv);
        getSupportActionBar().setTitle("Favourite Items");

        int [] temp = getValue();

        if(temp[0] == -1)
        {
            flag = true;
        }

        if(!flag) {

            if (minus > 0) {
                array = new int[temp.length - 1];
            }
            else {
                array = new int[temp.length];
            }

            for (int i = 0; i < array.length; i++) {
                array[i] = temp[i];
            }
            loadArray();

            totalWords.setText("Total Words : " + array.length);
            createTextView(array);
            Toast.makeText(getApplicationContext(), "Words loaded successfully!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            totalWords.setText("Total Words : " + 0);

            linear = (LinearLayout)findViewById(R.id.favourite_word_star);
            TextView[]tv = new TextView[1];
            tv[0] = new TextView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tv[0].setLayoutParams(params);
            tv[0].setTextSize(25);
            tv[0].setId(0);
            tv[0].setTextColor(Color.WHITE);

            tv[0].setText("We are extremely sorry!\nWe have nothign to show here!" +
                    "\nWhy don't you check some of our words and their meaning!!");
            linear.addView(tv[0]);

        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackVisible) {
                    setRightOut.setTarget(synonym);
                    setLeftIn.setTarget(linear);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;
                    btn_next.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private int[] getValue()
    {
        SharedPreferences settings;
        String temp = "";
        try {
            settings = this.getSharedPreferences("number", 0);
            temp = settings.getString("star2", "");
        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }
        if(temp.length() == 0)
        {
            return new int[]{-1};
        }
        else
        {
            flag = false;
            return arrayMake(temp);
        }
    }

    private int[] arrayMake(String temp)
    {
        String [] array = temp.split(",");
        int[]arrayReturn = new int[array.length-1];


        for(int i = 0; i < arrayReturn.length;i++)
        {
            arrayReturn[i] = Integer.parseInt(array[i+1]);
        }

        for(int i = 0; i < arrayReturn.length; i++)
        {
            for(int j = i + 1; j < arrayReturn.length; j++)
            {
                if(arrayReturn[i] == arrayReturn[j])
                {
                    arrayReturn[j] = -1;
                    minus++;
                }
            }
        }
        return arrayReturn;
    }

    private void createTextView(int []array) //animation work is here.
    {
        linear = (LinearLayout)findViewById(R.id.favourite_word_star);


        int maxLength = array.length;
        TextView[]tv = new TextView[maxLength];

        for (int i = 0; i < maxLength; i++) {


            int index = array[i];
            if(index != -1) {
                tv[i] = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                tv[i].setLayoutParams(params);
                tv[i].setTextSize(25);
                tv[i].setId(i);
                tv[i].setTextColor(Color.WHITE);


                tv[i].setText(i + 1 + ") " + word[index]);
                tv[i].setOnClickListener(eventHandler);
                //animation work is here
                tv[i].setPadding(0, 0, 0, 20);
                linear.addView(tv[i]);
            }
        }
    }

    View.OnClickListener eventHandler = new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {

            btn_next.setVisibility(View.VISIBLE);
            setRightOut.setTarget(linear);
            setLeftIn.setTarget(synonym);
            setRightOut.start();
            setLeftIn.start();
            isBackVisible = true;
            int i = (int)v.getId();

            int index = array[i];
            String words = word[index];
            String meaning = answer[index];
            synonym.setText(words + "\n\n"+meaning);

        }
    };








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

                Log.v("Hello", jsonString);
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
                Log.d("Debug", e + "");
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
}
