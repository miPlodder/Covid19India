package com.example.covid19.model.stateDistrictWiseJson;

import java.util.HashMap;

public class DistrictData {

    // maps district to its data
    HashMap<String, Cases> districtDataMap;

    public void putToDistrictDataMap(String district, Cases districtData) {
        if (districtDataMap == null) {
            districtDataMap = new HashMap<>();
        }
        districtDataMap.put(district, districtData);
    }

    public HashMap<String, Cases> getDistrictDataMap() {
        return districtDataMap;
    }

    @Override
    public String toString() {
        return "DistrictData{" +
                "districtDataMap=" + districtDataMap +
                '}';
    }
}
