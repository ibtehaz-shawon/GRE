package com.zerothtech.www.gre.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerothtech.www.gre.R;
import com.zerothtech.www.gre.utility.SwipeTouchListener;
import com.zerothtech.www.gre.utility.Utility;

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 8:22 PM
 * for Project GRE
 */
public class WordActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtWord, txtSynonym, txtTitleBar, txtWordCounter;
    private Context context;
    private ImageView imgBackBtn;
    private LinearLayout llEntireView;
    private static int counter;
    private String jsonString;
    private boolean isError;

    /**
     * on create view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_view);
        //**initializing the view objects/
        init();
        //**initializing the on click listener, setting init texts and setting visibility contexts/
        initVal();
    }


    /**
     * initializing the view
     */
    private void init() {
        context = this;
        isError = false;
        txtWord = (TextView)findViewById(R.id.txt_word);
        txtSynonym = (TextView)findViewById(R.id.txt_synonym);
        txtTitleBar = (TextView)findViewById(R.id.txt_app_title);
        txtWordCounter = (TextView)findViewById(R.id.txt_word_counter);
        imgBackBtn = (ImageView) findViewById(R.id.img_back_btn);
        llEntireView = findViewById(R.id.ll_word_view);
    }


    /**
     * initializing the on click listener for each object
     */
    @SuppressLint("SetTextI18n")
    private void initVal() {
        imgBackBtn.setOnClickListener(this);
        txtTitleBar.setText(context.getString(R.string.words));
        imgBackBtn.setVisibility(View.VISIBLE);
        txtSynonym.setVisibility(View.GONE);
        counter = 0;
        jsonString = new Utility().jsonLoader(context, context.getString(R.string.word_filename));
        String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
        if (tempWord == null) {
            txtWord.setText(context.getString(R.string.error_message));
            txtWordCounter.setText("");
            isError = true;
        } else {
            txtWord.setText(context.getString(R.string.tag_word)+ tempWord);
            txtWordCounter.setText(context.getString(R.string.words)
                    +" : "
                    +(counter + 1));
        }
        //** this function implement and execute swipe LEFT and RIGHT on the go for Linear Layout.
        swipeClickListener();
    }


    /**
     * on click listener implementation
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_btn:
                finish();
                break;
        }
    }


    /**
     * click handler for view change
     */
    @SuppressLint("SetTextI18n")
    private void linearViewChanger() {
        if (txtSynonym.getVisibility() == View.VISIBLE) {
            txtSynonym.setVisibility(View.GONE);
             String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
            if (tempWord == null) {
                txtWord.setText(context.getString(R.string.error_message));
                txtWordCounter.setText("");
                isError = true;
            } else {
                txtWord.setText(context.getString(R.string.tag_word)+ tempWord);
                txtWordCounter.setText(context.getString(R.string.words)
                        +" : "
                        +(counter + 1));
            }
        } else if (txtSynonym.getVisibility() == View.GONE) {
            txtSynonym.setVisibility(View.VISIBLE);
            String tempWord = new Utility().getWordsFromAsset(jsonString, counter, false);
            if (tempWord == null) {
                txtSynonym.setText(context.getString(R.string.error_message));
                txtWordCounter.setText("");
                isError = true;
            } else {
                txtSynonym.setText(context.getString(R.string.tag_meaning) + tempWord);
            }
        }
    }


    /**
     * Implement custom left-right swipe from @{@link SwipeTouchListener}
     */
    @SuppressLint("ClickableViewAccessibility")
    private void swipeClickListener() {
        llEntireView.setOnTouchListener(new SwipeTouchListener(context) {
            /**
             * this function will show the prev word of the list {Swipping Left}
             */
            @SuppressLint("SetTextI18n")
            public void onSwipeLeft() {
                //** go to new word
                if (isError) return;
                counter++;
                if (txtSynonym.getVisibility() == View.VISIBLE) {
                    txtSynonym.setVisibility(View.GONE);
                }
                String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
                if (tempWord == null) {
                    txtWord.setText(context.getString(R.string.error_message));
                    txtWordCounter.setText("");
                    isError = true;
                } else {
                    txtWord.setText(context.getString(R.string.tag_word)+ tempWord);
                    txtWordCounter.setText(context.getString(R.string.words)
                            +" : "
                            +(counter + 1));
                }
            }

            /**
             * this function will show the prev word of the list {Swipping Left}
             */
            @SuppressLint("SetTextI18n")
            public void onSwipeRight() {
                if (isError) return;
                if (counter != 0) counter--;
                else {
                    new Utility().makeToast(context, context.getString(R.string.end), 1);
                }
                if (txtSynonym.getVisibility() == View.VISIBLE) {
                    txtSynonym.setVisibility(View.GONE);
                }
                String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
                if (tempWord == null) {
                    txtWord.setText(context.getString(R.string.error_message));
                    txtWordCounter.setText("");
                    isError = true;
                } else {
                    txtWord.setText(context.getString(R.string.tag_word)+ tempWord);
                    txtWordCounter.setText(context.getString(R.string.words)
                            +" : "
                            +(counter + 1));
                }
            }

            /**
             * handles the on click listener here
             */
            public void onClick() {
                if (!isError) linearViewChanger();
            }
        });
    }
}
