package com.example.lenovo.healthyheather;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.healthyheather.Contract.ContractAPIConstants;
import com.example.lenovo.healthyheather.Networking.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private Button b_get;
    private TrackGPS gps;
    double longitude;
    double latitude;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_get = (Button)findViewById(R.id.get);
        gps = new TrackGPS(MainActivity.this);
        if(gps.canGetLocation()){
            longitude = gps.getLongitude();
            latitude = gps .getLatitude();
//                    Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {
            gps.showSettingsAlert();
        }

        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog = new ProgressDialog(MainActivity.this);
                mProgressDialog.setTitle("Fetching Nearest Hospitals");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();

                Call<JsonReturn> getHospitalListCall= ApiClient.getApiInterface().getHospitalListByState(ContractAPIConstants.API_KEY,ContractAPIConstants.RESOURCE_ID,"Gujarat");
                getHospitalListCall.enqueue(new Callback<JsonReturn>() {
                    @Override
                    public void onResponse(Call<JsonReturn> call, Response<JsonReturn> response) {
                        mProgressDialog.dismiss();
                        if(response.isSuccessful()&&response.body().hospitalsArrayList.size()>0){
                            ArrayList<HospitalInfo> myList=response.body().hospitalsArrayList;

                            Toast.makeText(MainActivity.this, myList.get(0).District, Toast.LENGTH_SHORT).show();
                            Log.i("phase1", myList.get(0).Hospital_Name);

                        }else{
                            Log.i("phase1", "hospital search unsuccess");
                            Toast.makeText(MainActivity.this, "data for hospital in "+"Gujarat"+" state is currently unavailable, try after some time", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonReturn> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Log.i("phase1", "Hospital search failed");
                        Toast.makeText(MainActivity.this, "Please check ur Internet Connection, Hospital List couldn't be loaded", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }




//    }
}
