package com.example.lenovo.healthyheather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Zeus on 11-Feb-17.
 */

public class JsonReturn {
    @SerializedName("records")
    public ArrayList<HospitalInfo> hospitalsArrayList;
}
