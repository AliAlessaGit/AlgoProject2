package Front_End;
import Back_End.Network;
import Back_End.Station;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StationsPanel extends JPanel {

    private Network network;

    private JTable table;

    private StationsTableModel model;

    public StationsPanel(Network network) {

        this.network = network;

        initializeUI();
    }

    private void initializeUI() {

        setLayout(
                new BorderLayout(10,10));

        setBackground(
                Theme.BACKGROUND);

        JLabel title =
                new JLabel(
                        "Stations Management");

        title.setForeground(
                Theme.TEXT);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24));

        add(title,
                BorderLayout.NORTH);

        model =
                new StationsTableModel(
                        network.getStations());

        table =
                new JTable(model);

        JScrollPane scroll =
                new JScrollPane(table);

        add(scroll,
                BorderLayout.CENTER);

        JPanel buttons =
                new JPanel();

        JButton addButton =
                new JButton("Add");

        JButton editButton =
                new JButton("Edit");

        JButton deleteButton =
                new JButton("Delete");

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        add(buttons,
                BorderLayout.SOUTH);

        addButton.addActionListener(
                e -> addStation());

        deleteButton.addActionListener(
                e -> deleteStation());
    }

    private void addStation() {

        JFrame frame =
                (JFrame)
                        SwingUtilities
                                .getWindowAncestor(this);

        AddStationDialog dialog =
                new AddStationDialog(frame);

        dialog.setVisible(true);

        if(dialog.isConfirmed()) {

            String name =
                    dialog.getStationName();

            network.addStation(
                    new Station(name));

            refreshTable();
        }
    }

    private void deleteStation() {

        int row =
                table.getSelectedRow();

        if(row == -1)
            return;

        Station station =
                model.getStation(row);

        network.removeStation(
                station.name);

        refreshTable();
    }

    private void refreshTable() {

        model =
                new StationsTableModel(
                        network.getStations());

        table.setModel(model);

        repaint();
    }
}