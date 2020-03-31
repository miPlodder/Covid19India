package covid19.example.covid19.model.dataJson;

import com.google.gson.annotations.SerializedName;

public class Statewise {

    private String active;

    private String confirmed;

    private String deaths;

    @SerializedName("delta")
    private Delta delta;

    private String lastupdatedtime;

    private String recovered;

    private String state;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Statewise{" +
                "active='" + active + '\'' +
                ", confirmed='" + confirmed + '\'' +
                ", deaths='" + deaths + '\'' +
                ", delta=" + delta +
                ", lastupdatedtime='" + lastupdatedtime + '\'' +
                ", recovered='" + recovered + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
