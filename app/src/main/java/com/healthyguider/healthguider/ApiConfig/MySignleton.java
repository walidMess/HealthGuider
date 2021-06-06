package com.healthyguider.healthguider.ApiConfig;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wezzy on 23/03/2018.
 */

public class MySignleton {

    private static MySignleton mInstance;
    private RequestQueue requestQueue;
    private static Context ctx;

    MySignleton (Context context){
        ctx=context;
        requestQueue = getRequestQueue();
    }


    public  RequestQueue getRequestQueue(){

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return  requestQueue;

    }
    public static synchronized  MySignleton getInstance(Context context){



        if(mInstance==null){
            mInstance= new MySignleton(context);

        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){


        requestQueue.add(request);


    }


}
