package covid19.example.covid19.model.dataJson;

public class Tested {

    private String source;

    private String totalindividualstested;

    private String totalpositivecases;

    private String totalsamplestested;

    private String updatetimestamp;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTotalindividualstested() {
        return totalindividualstested;
    }

    public void setTotalindividualstested(String totalindividualstested) {
        this.totalindividualstested = totalindividualstested;
    }

    public String getTotalpositivecases() {
        return totalpositivecases;
    }

    public void setTotalpositivecases(String totalpositivecases) {
        this.totalpositivecases = totalpositivecases;
    }

    public String getTotalsamplestested() {
        return totalsamplestested;
    }

    public void setTotalsamplestested(String totalsamplestested) {
        this.totalsamplestested = totalsamplestested;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }

    public void setUpdatetimestamp(String updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
    }

    @Override
    public String toString() {
        return "Tested{" +
                "source='" + source + '\'' +
                ", totalindividualstested='" + totalindividualstested + '\'' +
                ", totalpositivecases='" + totalpositivecases + '\'' +
                ", totalsamplestested='" + totalsamplestested + '\'' +
                ", updatetimestamp='" + updatetimestamp + '\'' +
                '}';
    }
}
