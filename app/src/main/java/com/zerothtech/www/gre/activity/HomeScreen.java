package com.zerothtech.www.gre.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zerothtech.www.gre.R;

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 4:40 PM
 * for Project GRE
 */
public class HomeScreen extends BaseActivity implements View.OnClickListener {


    private Button btnWords, btnFav, btnTests;
    private Context context;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        //**initializing the view objects
        init();
        //**initializing the view with init value
        initVal();
    }

    /**
     * initializing the view
     */
    private void init() {
        context = this;
        btnWords = findViewById(R.id.btn_words);
        btnFav = findViewById(R.id.btn_fav);
        btnTests = findViewById(R.id.btn_tests);
    }


    /**
     * initializing with primary value and setting up on click listener.
     */
    private void initVal() {
        btnWords.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnTests.setOnClickListener(this);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_words:
                Intent i = new Intent(this, WordActivity.class);
                context.startActivity(i);
                break;
            case R.id.btn_fav:
                break;
            case R.id.btn_tests:
                break;
        }
    }
}
