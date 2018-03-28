package com.zerothtech.www.gre.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ibtehaz Shawon on
 * 3/28/18 - 4:41 PM
 * for Project GRE
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
//    private PreferenceUtil mMobileData = null;
    private String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mMobileData = PreferenceUtil.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Initialize the loader for Child class whenever necessary.
     */
    public void initProgressLoader() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    /**
     * Sets whether this dialog is cancelable with the
     */
    protected void setProgressCancelable(boolean isCancelable) {
        if (progressDialog != null) {
            progressDialog.setCancelable(isCancelable);
        }
    }

    /**
     * Show progress dialog.
     *
     * @param message The message show in the progress dialog initially.
     */
    public void showDialog(String message) {
        if (progressDialog == null) {
            initProgressLoader();
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * Cancel progress dialog.
     */
    public void cancelDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Cancel progress dialog.
     */
    public boolean isDialogShowing() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        } else return false;
    }
}
