package com.zerothtech.www.gre.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zerothtech.www.gre.R;

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 4:40 PM
 * for Project GRE
 */
public class HomeScreen extends BaseActivity implements View.OnClickListener {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
