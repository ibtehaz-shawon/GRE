package com.zerothtech.www.gre.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zerothtech.www.gre.R;

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 8:22 PM
 * for Project GRE
 */
public class WordActivity extends BaseActivity implements View.OnClickListener {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_view);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
