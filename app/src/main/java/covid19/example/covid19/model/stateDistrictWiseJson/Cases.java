package covid19.example.covid19.model.stateDistrictWiseJson;

public class Cases {

    private String confirmed;
    private String lastupdatedtime;

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Override
    public String toString() {
        return "Cases{" +
                "confirmed='" + confirmed + '\'' +
                ", lastupdatedtime='" + lastupdatedtime + '\'' +
                '}';
    }
}
