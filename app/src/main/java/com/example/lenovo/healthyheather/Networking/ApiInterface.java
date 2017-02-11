package com.example.lenovo.healthyheather.Networking;

import com.example.lenovo.healthyheather.JsonReturn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zeus on 4/18/2016.
 */
public interface ApiInterface {

    @GET("resource.json")
    Call<JsonReturn> getHospitalList(@Query("api-key") String API_KEY,
                                           @Query("resource_id") String RESOURCE_ID,
                                           @Query("offset") int offs);
}
