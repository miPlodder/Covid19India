package covid19.example.covid19.model.dataJson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataJSON {

    @SerializedName("cases_time_series")
    private List<CasesTimeSeries> casesTimeSeriesList;

    @SerializedName("key_values")
    private List<KeyValues> keyValuesList;

    @SerializedName("statewise")
    private List<Statewise> statewiseList;

    @SerializedName("tested")
    private List<Tested> testedList;

    public List<CasesTimeSeries> getCasesTimeSeriesList() {
        return casesTimeSeriesList;
    }

    public void setCasesTimeSeriesList(List<CasesTimeSeries> casesTimeSeriesList) {
        this.casesTimeSeriesList = casesTimeSeriesList;
    }

    public List<KeyValues> getKeyValuesList() {
        return keyValuesList;
    }

    public void setKeyValuesList(List<KeyValues> keyValuesList) {
        this.keyValuesList = keyValuesList;
    }

    public List<Statewise> getStatewiseList() {
        return statewiseList;
    }

    public void setStatewiseList(List<Statewise> statewiseList) {
        this.statewiseList = statewiseList;
    }

    public List<Tested> getTestedList() {
        return testedList;
    }

    public void setTestedList(List<Tested> testedList) {
        this.testedList = testedList;
    }

    @Override
    public String toString() {
        return "DataJSON{" +
                "casesTimeSeriesList=" + casesTimeSeriesList +
                ", keyValuesList=" + keyValuesList +
                ", statewiseList=" + statewiseList +
                ", testedList=" + testedList +
                '}';
    }
}
