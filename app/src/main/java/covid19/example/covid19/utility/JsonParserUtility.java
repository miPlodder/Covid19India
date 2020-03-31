package covid19.example.covid19.utility;

import covid19.example.covid19.model.stateDistrictWiseJson.Cases;
import covid19.example.covid19.model.stateDistrictWiseJson.DistrictData;
import covid19.example.covid19.model.stateDistrictWiseJson.StateDistrictWiseJson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonParserUtility {

    public static final String TAG = JsonParserUtility.class.getClass().getSimpleName();
    public static final String DISTRICT_DATA = "districtData";

    public static StateDistrictWiseJson parseStateDistrictWiseResponse(JsonObject jsonObject) {

        StateDistrictWiseJson stateDistrictWiseJson = new StateDistrictWiseJson();
        for (String state : jsonObject.keySet()) {
            JsonObject districtJsonObject = jsonObject.getAsJsonObject(state).getAsJsonObject(DISTRICT_DATA);
            DistrictData districtData = new DistrictData();
            for (String district : districtJsonObject.keySet()) {
                JsonObject districtDataJsonObject = districtJsonObject.getAsJsonObject(district);
                Cases cases = (new Gson()).fromJson(districtDataJsonObject, Cases.class);
                districtData.putToDistrictDataMap(district, cases);
            }
            stateDistrictWiseJson.putToStateDistrictMap(state, districtData);
        }
        return stateDistrictWiseJson;
    }

}
