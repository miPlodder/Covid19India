package com.example.covid19.model.stateDistrictWiseJson;

import java.util.HashMap;

public class StateDistrictWiseJson {

    // maps state to district
    HashMap<String, DistrictData> stateDistrictMap;

    public void putToStateDistrictMap(String state, DistrictData districtData) {
        if (stateDistrictMap == null) {
            stateDistrictMap = new HashMap<>();
        }
        stateDistrictMap.put(state, districtData);
    }

    @Override
    public String toString() {
        return "StateDistrictWiseJson{" +
                "stateDistrictMap=" + stateDistrictMap +
                '}';
    }
}