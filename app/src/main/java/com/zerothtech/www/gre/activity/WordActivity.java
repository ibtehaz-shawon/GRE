package com.zerothtech.www.gre.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackandphantom.androidlikebutton.AndroidLikeButton;
import com.zerothtech.www.gre.R;
import com.zerothtech.www.gre.utility.SwipeTouchListener;
import com.zerothtech.www.gre.utility.Utility;



/**
 * TODO: 1) Keeping track of running word.
 * TODO: 2) Animation Fav
 * TODO: 3) Keeping the favourites data in sqlite
 */

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 8:22 PM
 * for Project GRE
 */
public class WordActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtWord, txtMeaning, txtTitleBar, txtWordCounter;
    private Context context;
    private ImageView imgBackBtn;
    private LinearLayout llEntireView;
    private static int counter;
    private String jsonString;
    private boolean isError;
    private AndroidLikeButton imgFavWord;
    private boolean isLiked;

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
        isLiked = false;
        txtWord = (TextView)findViewById(R.id.txt_word);
        txtMeaning = (TextView)findViewById(R.id.txt_meaning);
        txtTitleBar = (TextView)findViewById(R.id.txt_app_title);
        txtWordCounter = (TextView)findViewById(R.id.txt_word_counter);
        imgBackBtn = (ImageView) findViewById(R.id.img_back_btn);
        llEntireView = findViewById(R.id.ll_word_view);
        imgFavWord = (AndroidLikeButton)findViewById(R.id.img_fav_btn);
    }


    /**
     * initializing the on click listener for each object
     */
    @SuppressLint("SetTextI18n")
    private void initVal() {
        imgBackBtn.setOnClickListener(this);
        imgFavWord.setOnClickListener(this);
        txtTitleBar.setText(context.getString(R.string.words));
        imgBackBtn.setVisibility(View.VISIBLE);
        txtMeaning.setVisibility(View.GONE);
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
            case R.id.img_fav_btn:
                favouriteWord();
                break;
        }
    }


    /**
     * click handler for view change
     */
    @SuppressLint("SetTextI18n")
    private void linearViewChanger() {
        if (txtMeaning.getVisibility() == View.VISIBLE) {
            txtMeaning.setVisibility(View.GONE);
             String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
            if (tempWord == null) {
                txtWord.setText(context.getString(R.string.error_message));
                new Utility().makeToast(context, context.getString(R.string.error_message), 1,3);
                txtWordCounter.setText("");
                isError = true;
            } else {
                txtWord.setText(context.getString(R.string.tag_word)+ tempWord);
                txtWordCounter.setText(context.getString(R.string.words)
                        +" : "
                        +(counter + 1));
            }
        } else if (txtMeaning.getVisibility() == View.GONE) {
            txtMeaning.setVisibility(View.VISIBLE);
            String tempWord = new Utility().getWordsFromAsset(jsonString, counter, false);
            if (tempWord == null) {
                txtMeaning.setText(context.getString(R.string.error_message));
                new Utility().makeToast(context, context.getString(R.string.error_message), 1,3);
                txtWordCounter.setText("");
                isError = true;
            } else {
                txtMeaning.setText(context.getString(R.string.tag_meaning) + tempWord);
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
                if (counter == 1502) {
                    new Utility().makeToast(context, context.getString(R.string.other_end), 1,0);
                    return;
                }
                if (txtMeaning.getVisibility() == View.VISIBLE) {
                    txtMeaning.setVisibility(View.GONE);
                }
                String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
                if (tempWord == null) {
                    txtWord.setText(context.getString(R.string.error_message));
                    new Utility().makeToast(context, context.getString(R.string.error_message), 1,3);
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
                    new Utility().makeToast(context, context.getString(R.string.end), 1,1);
                    return;
                }
                if (txtMeaning.getVisibility() == View.VISIBLE) {
                    txtMeaning.setVisibility(View.GONE);
                }
                String tempWord = new Utility().getWordsFromAsset(jsonString, counter, true);
                if (tempWord == null) {
                    txtWord.setText(context.getString(R.string.error_message));
                    new Utility().makeToast(context, context.getString(R.string.error_message), 1,3);
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

            /**
             * handles the double click to mark a word as favourite
             */
            public void onLongPressEvent() {
                String word = new Utility().getWordsFromAsset(jsonString, counter, true);
                String meaning = new Utility().getWordsFromAsset(jsonString, counter, false);
                new Utility().makeToast(context, "Word : "+word + " >> meaning : "+meaning, 1, 0);
            }
        });
    }


    /**
     * this function handles marking a word as fav
     */
    private void favouriteWord() {
        Bitmap icLikeBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_full_heart);
        Bitmap icDislikeBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_no_heart);
        if (isLiked) {
            imgFavWord.setLikeIcon(icDislikeBmp);
            isLiked = false;
        } else {
            isLiked = true;
            imgFavWord.setLikeIcon(icLikeBmp);
        }
    }
}
