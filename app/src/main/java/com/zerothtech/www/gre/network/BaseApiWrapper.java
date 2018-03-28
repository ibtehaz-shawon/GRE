package com.zerothtech.www.gre.network;

import com.android.volley.RequestQueue;
import com.zerothtech.www.gre.application.GRE;

/**
 *
 */

public abstract class BaseApiWrapper {

    public RequestQueue requestQueue;

    public BaseApiWrapper() {
        requestQueue = GRE.getInstance().getRequestQueue();
    }

    public abstract void callApi(String url);
}