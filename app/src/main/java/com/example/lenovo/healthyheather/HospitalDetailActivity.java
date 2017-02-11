package com.example.lenovo.healthyheather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HospitalDetailActivity extends AppCompatActivity {

    HospitalInfo a=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        Intent i=getIntent();
        a= (HospitalInfo) i.getSerializableExtra("hospital");

        TextView Name= (TextView) findViewById(R.id.T1);
        TextView Add= (TextView) findViewById(R.id.T2);
        TextView Spec= (TextView) findViewById(R.id.T3);



        Name.setText(a.Hospital_Name);
        Add.setText(a.Location);
        Spec.setText(a.Specialties);

    }
}
