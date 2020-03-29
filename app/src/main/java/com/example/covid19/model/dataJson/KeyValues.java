package com.example.covid19.model.dataJson;

public class KeyValues {

    private String confirmeddelta;

    private String counterforautotimeupdate;

    private String deceaseddelta;

    private String lastupdatedtime;

    private String recovereddelta;

    private String statesdelta;

    public String getConfirmeddelta() {
        return confirmeddelta;
    }

    public void setConfirmeddelta(String confirmeddelta) {
        this.confirmeddelta = confirmeddelta;
    }

    public String getCounterforautotimeupdate() {
        return counterforautotimeupdate;
    }

    public void setCounterforautotimeupdate(String counterforautotimeupdate) {
        this.counterforautotimeupdate = counterforautotimeupdate;
    }

    public String getDeceaseddelta() {
        return deceaseddelta;
    }

    public void setDeceaseddelta(String deceaseddelta) {
        this.deceaseddelta = deceaseddelta;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getRecovereddelta() {
        return recovereddelta;
    }

    public void setRecovereddelta(String recovereddelta) {
        this.recovereddelta = recovereddelta;
    }

    public String getStatesdelta() {
        return statesdelta;
    }

    public void setStatesdelta(String statesdelta) {
        this.statesdelta = statesdelta;
    }

    @Override
    public String toString() {
        return "KeyValues{" +
                "confirmeddelta='" + confirmeddelta + '\'' +
                ", counterforautotimeupdate='" + counterforautotimeupdate + '\'' +
                ", deceaseddelta='" + deceaseddelta + '\'' +
                ", lastupdatedtime='" + lastupdatedtime + '\'' +
                ", recovereddelta='" + recovereddelta + '\'' +
                ", statesdelta='" + statesdelta + '\'' +
                '}';
    }
}
