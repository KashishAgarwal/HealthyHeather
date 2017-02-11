package com.example.lenovo.healthyheather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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
    Handler handler;
    Runnable runnable;
    double latitude;
    boolean cont;
    ArrayList<HospitalInfo> completeList;
    ProgressDialog mProgressDialog;

    boolean shouldAdd(String coord){
        String[] tmp=coord.split(",");
        try {
            Double lat = Double.parseDouble(tmp[0]);
            Double y = Double.parseDouble(tmp[1]);
            boolean poss = true;
            if (Math.abs(lat - latitude) > 0.8)
                poss = false;
            if (Math.abs(y - longitude) > 0.8)
                poss = false;
            return poss;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont=true;
        completeList=new ArrayList<>();
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

                Log.i("getLocation","is "+latitude+", "+longitude);
                mProgressDialog = new ProgressDialog(MainActivity.this);
                mProgressDialog.setTitle("Fetching Nearest Hospitals");
                mProgressDialog.setMessage("Loading...");

                int offset=0;
                while(cont && offset<100){
                    Call<JsonReturn> getHospitalListCall= ApiClient.getApiInterface()
                            .getHospitalList(ContractAPIConstants.API_KEY,ContractAPIConstants.RESOURCE_ID,offset);
                    getHospitalListCall.enqueue(new Callback<JsonReturn>() {
                        @Override
                        public void onResponse(Call<JsonReturn> call, Response<JsonReturn> response) {

                            if(response.isSuccessful()&&response.body().hospitalsArrayList!=null&&response.body().hospitalsArrayList.size()>0){
                                ArrayList<HospitalInfo> myList=response.body().hospitalsArrayList;
                                for(HospitalInfo cur:myList) {
                                    if(!cur.Location_Coordinates.equals("NA"))
                                        if(shouldAdd(cur.Location_Coordinates))
                                            completeList.add(cur);
                                }

//                            Toast.makeText(MainActivity.this, myList.get(0).District, Toast.LENGTH_SHORT).show();
//                            Log.i("phase1", myList.get(0).Hospital_Name);
                            }else{
                                cont=false;
//                            Log.i("phase1", "hospital search unsuccess");
//                            Toast.makeText(MainActivity.this, "data for hospital in "+"Gujarat"+" state is currently unavailable, try after some time", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonReturn> call, Throwable t) {
                            cont=false;
//                        Log.i("phase1", "Hospital search failed");
//                        Toast.makeText(MainActivity.this, "Please check ur Internet Connection, Hospital List couldn't be loaded", Toast.LENGTH_LONG).show();
                        }
                    });
                    ++offset;
                }

                Toast.makeText(MainActivity.this, "num= "+completeList.size(), Toast.LENGTH_SHORT).show();

                handler = new Handler();


                        // TODO Auto-generated method stub

                        runnable = new Runnable() {

                            @Override
                            public void run() {

                                Intent i=new Intent();
                                startActivity(i);
                            }
                        };
                        handler.postDelayed(runnable, 7000);

                    }




        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }


}
