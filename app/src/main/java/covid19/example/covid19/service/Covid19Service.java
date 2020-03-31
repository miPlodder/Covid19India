package covid19.example.covid19.service;

import covid19.example.covid19.model.dataJson.DataJSON;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Covid19Service {

    @GET("/data.json")
    Call<DataJSON> getDataJSON();

    @GET("/state_district_wise.json")
    Call<JsonObject> getStateDistrictWiseJSON();
}
