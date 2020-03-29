package com.example.covid19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.covid19.R;
import com.example.covid19.model.dataJson.DataJSON;
import com.example.covid19.service.Covid19Client;
import com.example.covid19.service.Covid19Service;
import com.example.covid19.utility.JsonParserUtility;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Covid19Service service = Covid19Client.getCovid19Client().create(Covid19Service.class);

        Call<DataJSON> response = service.getDataJSON();
        response.enqueue(new Callback<DataJSON>() {

            @Override
            public void onResponse(Call<DataJSON> call, Response<DataJSON> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<DataJSON> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        Call<JsonObject> response2 = service.getStateDistrictWiseJSON();
        response2.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse: " + JsonParserUtility.parseStateDistrictWiseResponse(response.body()));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
