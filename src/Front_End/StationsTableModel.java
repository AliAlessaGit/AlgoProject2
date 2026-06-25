package Front_End;
import Back_End.Station;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class StationsTableModel
        extends AbstractTableModel {

    private ArrayList<Station> stations;

    public StationsTableModel(
            ArrayList<Station> stations) {

        this.stations = stations;
    }

    @Override
    public int getRowCount() {

        return stations.size();
    }

    @Override
    public int getColumnCount() {

        return 2;
    }

    @Override
    public String getColumnName(int column) {

        if(column == 0)
            return "Name";

        return "Symbol";
    }

    @Override
    public Object getValueAt(
            int row,
            int column) {

        Station station =
                stations.get(row);

        if(column == 0)
            return station.name;

        return station.symbol;
    }

    public Station getStation(
            int row) {

        return stations.get(row);
    }
}