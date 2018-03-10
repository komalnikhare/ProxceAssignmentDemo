package com.android.proxce.proxcedemoapp.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Created by Komal on 3/10/2018.
 */

public class RequestUtils {

   public static void addRequest(Context context, JsonObjectRequest request){
     MySingleton.getInstance(context).addToRequestQueue(request);
     request.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
   }

}
