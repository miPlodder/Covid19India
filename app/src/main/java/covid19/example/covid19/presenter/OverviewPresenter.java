package covid19.example.covid19.presenter;

import android.util.Log;

import covid19.example.covid19.activity.OverviewActivity;
import covid19.example.covid19.model.dataJson.DataJSON;
import covid19.example.covid19.model.stateDistrictWiseJson.StateDistrictWiseJson;
import covid19.example.covid19.service.Covid19Client;
import covid19.example.covid19.service.Covid19Service;
import covid19.example.covid19.utility.JsonParserUtility;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewPresenter {

    private Covid19Service service = null;
    private OverviewActivity activity;
    DataJSON dataJSON = null;
    StateDistrictWiseJson stateDistrictWiseJson = null;

    public final static String TAG = OverviewPresenter.class.getClass().getSimpleName();

    public OverviewPresenter(OverviewActivity activity) {
        service = Covid19Client.getCovid19Client().create(Covid19Service.class);
        this.activity = activity;
    }

    public void getDataJSON() {
        if (dataJSON == null) {
            Call<DataJSON> response = service.getDataJSON();

            response.enqueue(new Callback<DataJSON>() {

                @Override
                public void onResponse(Call<DataJSON> call, Response<DataJSON> response) {
                    dataJSON = response.body();
                    activity.setDataToAdapter(dataJSON);
                    Log.d(TAG, "onResponse: " + dataJSON.getStatewiseList().toString());
                }

                @Override
                public void onFailure(Call<DataJSON> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void getStateDistrictWiseJSON() {
        if (stateDistrictWiseJson == null) {
            Call<JsonObject> response = service.getStateDistrictWiseJSON();
            response.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    stateDistrictWiseJson = JsonParserUtility.parseStateDistrictWiseResponse(response.body());
                    activity.setStateDistrictWiseJson(stateDistrictWiseJson);
                    Log.d(TAG, "onResponse: " + stateDistrictWiseJson);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }

    }
}
