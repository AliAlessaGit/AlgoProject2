package Front_End.Components;

import Back_End.Station;
import Front_End.Theme;

import javax.swing.*;
import java.awt.*;

public class StationCard extends CustomCard {

    private Station station;

    private JButton editButton;

    private JButton deleteButton;

    public StationCard(Station station) {

        this.station = station;

        buildUI();
    }

    private void buildUI() {

        setLayout(new BorderLayout(10,10));

        JLabel nameLabel =
                new JLabel(station.name);

        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20));

        nameLabel.setForeground(
                Theme.TEXT);

        JLabel symbolLabel =
                new JLabel(
                        "Symbol : "
                                + station.symbol);

        symbolLabel.setForeground(
                Theme.TEXT_SECONDARY);

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        buttonPanel.setOpaque(false);

        editButton =
                new ModernButton("Edit");

        deleteButton =
                new ModernButton("Delete");

        buttonPanel.add(editButton);

        buttonPanel.add(deleteButton);

        add(nameLabel,
                BorderLayout.NORTH);

        add(symbolLabel,
                BorderLayout.CENTER);

        add(buttonPanel,
                BorderLayout.SOUTH);
    }

    public JButton getEditButton() {

        return editButton;
    }

    public JButton getDeleteButton() {

        return deleteButton;
    }

    public Station getStation() {

        return station;
    }
}