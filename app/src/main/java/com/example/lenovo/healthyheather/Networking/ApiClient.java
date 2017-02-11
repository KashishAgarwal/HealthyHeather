package com.example.lenovo.healthyheather.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeus on 4/18/2016.
 */
public class ApiClient {
    private static ApiInterface mService;
    public static ApiInterface getApiInterface()
    {
        if(mService==null) {
            Gson gson = new GsonBuilder().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://data.gov.in/api/datastore/")
                    .addConverterFactory(GsonConverterFactory
                            .create(gson)).build();


            mService = retrofit.create(ApiInterface.class);
        }   return mService;

    }
}
