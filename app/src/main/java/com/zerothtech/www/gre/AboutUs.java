package com.zerothtech.www.gre;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setTitle("Who are we");

        TextView footer = (TextView)findViewById(R.id.about_footer);

        String emoticon = getEmoticon(0x2764);
        String line = "Built with "+emoticon+" at ZerothTech Inc.";
        footer.setText(line);

        Button facebook = (Button) findViewById(R.id.facebook);
        Button web = (Button)findViewById(R.id.web);
        Button twitter = (Button)findViewById(R.id.twitter);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.fb.com/ZerothTech");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.zerothTech.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.twitter.com/zerothTech");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        TextView aboutUs = (TextView)findViewById(R.id.about_text);
        String line2 = "We are bunch of pizza lover, caffeine addict ninja who are passionate about programming.\n" +
                "It starts with an impossible idea & ends with this magnificent design";
        aboutUs.setText(line2);




    }

    private String getEmoticon(int unicode)
    {
        return new String(Character.toChars(unicode));
    }
}
