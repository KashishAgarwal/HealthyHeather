package com.example.lenovo.healthyheather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HospitalDetailActivity extends AppCompatActivity {

    HospitalInfo curHospital=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        Intent i=getIntent();
        curHospital= (HospitalInfo) i.getSerializableExtra("hospital");

    }
}
