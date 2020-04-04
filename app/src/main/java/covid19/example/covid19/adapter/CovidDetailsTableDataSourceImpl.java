package covid19.example.covid19.adapter;

import covid19.example.covid19.model.dataJson.DataJSON;
import covid19.example.covid19.model.dataJson.Statewise;

public class CovidDetailsTableDataSourceImpl implements CovidDetailsTableDataSource<String, String, String, String> {

    private DataJSON json;
    public static final String TAG = CovidDetailsTableDataSourceImpl.class.getClass().getSimpleName();
    private String[] columns = {"State", "Confirmed", "Active", "Decreased", "Recovered"};

    public CovidDetailsTableDataSourceImpl(DataJSON json) {
        this.json = json;
        boolean insideIf = false;
        for (int i = 0; i < json.getStatewiseList().size(); i++) {
            if (json.getStatewiseList().get(i).getState().equals("Total")) {
                insideIf = true;
                json.getStatewiseList().remove(i);
                break;
            }
        }
        if (insideIf) {
            json.getStatewiseList().add(0, new Statewise());
        }
    }

    @Override
    public int getRowsCount() {
        return json.getStatewiseList().size();
    }

    @Override
    public int getColumnsCount() {
        return columns.length;
    }

    @Override
    public String getFirstHeaderData() {
        return columns[0];
    }

    @Override
    public String getRowHeaderData(int index) {
        return json.getStatewiseList().get(index).getState();
    }

    @Override
    public String getColumnHeaderData(int index) {
        return columns[index];
    }

    @Override
    public String getItemData(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return json.getStatewiseList().get(rowIndex).getState();
            case 1:
                return json.getStatewiseList().get(rowIndex).getConfirmed();
            case 2:
                return json.getStatewiseList().get(rowIndex).getActive();
            case 3:
                return json.getStatewiseList().get(rowIndex).getDeaths();
            case 4:
                return json.getStatewiseList().get(rowIndex).getRecovered();
        }
        return null;
    }

    public DataJSON getJson() {
        return json;
    }
}
