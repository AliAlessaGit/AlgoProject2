package Front_End.Pages;

import Back_End.Network;
import Back_End.Station;
import Front_End.Theme;
import Front_End.Components.*;
import Front_End.Dialogs.AddStationDialog;

import javax.swing.*;
import java.awt.*;

public class StationsPanel extends JPanel {

    private Network network;

    private JPanel cardsPanel;

    public StationsPanel(Network network) {

        this.network = network;

        buildUI();

        refreshStations();
    }

    private void buildUI() {

        setBackground(
                Theme.BACKGROUND);

        setLayout(
                new BorderLayout(
                        15,
                        15));

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20));

        JPanel top =
                new JPanel(
                        new BorderLayout());

        top.setOpaque(false);

        top.add(
                new PageTitle(
                        "Stations"),
                BorderLayout.WEST);

        ModernButton addButton =
                new ModernButton(
                        "+ Add Station");

        top.add(
                addButton,
                BorderLayout.EAST);

        add(top,
                BorderLayout.NORTH);

        cardsPanel =
                new JPanel();

        cardsPanel.setOpaque(false);

        cardsPanel.setLayout(
                new GridLayout(
                        0,
                        3,
                        15,
                        15));

        JScrollPane scrollPane =
                new JScrollPane(
                        cardsPanel);

        scrollPane.setBorder(null);

        add(scrollPane,
                BorderLayout.CENTER);

        addButton.addActionListener(
                e -> addStation());
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

            String stationName =
                    dialog.getStationName();

            if(stationName.isBlank())
                return;

            network.addStation(
                    new Station(
                            stationName));

            refreshStations();
        }
    }

    private void refreshStations() {

        cardsPanel.removeAll();

        for(Station station :
                network.getStations()) {

            StationCard card =
                    new StationCard(
                            station);

            card.getDeleteButton()
                    .addActionListener(
                            e -> {

                                network.removeStation(
                                        station.name);

                                refreshStations();
                            });

            cardsPanel.add(card);
        }

        cardsPanel.revalidate();

        cardsPanel.repaint();
    }
}