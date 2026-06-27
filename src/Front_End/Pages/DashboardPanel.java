package Front_End.Pages;

import Back_End.Network;
import Back_End.NetworkListener;
import Front_End.Theme;
import Front_End.Components.*;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel implements NetworkListener {

    private final Network network;

    private JLabel stationsLabel;
    private JLabel routesLabel;
    private JLabel cyclesLabel;
    private JLabel statusLabel;

    public DashboardPanel(Network network) {

        this.network = network;

        network.addListener(this);

        setBackground(Theme.BACKGROUND);

        setLayout(new BorderLayout(20,20));

        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        add(new PageTitle("Dashboard"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2,2,20,20));

        cards.setOpaque(false);

        stationsLabel = new JLabel();
        routesLabel = new JLabel();
        cyclesLabel = new JLabel();
        statusLabel = new JLabel();

        cards.add(createInfoCard("Stations", stationsLabel));
        cards.add(createInfoCard("Routes", routesLabel));
        cards.add(createInfoCard("Cycles", cyclesLabel));
        cards.add(createInfoCard("Status", statusLabel));

        add(cards, BorderLayout.CENTER);

        refreshDashboard();
    }

    private CustomCard createInfoCard(String title, JLabel valueLabel){

        CustomCard card = new CustomCard();

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(Theme.TEXT_SECONDARY);

        titleLabel.setFont(Theme.SUBTITLE_FONT);

        valueLabel.setForeground(Theme.ACCENT);

        valueLabel.setFont(new Font("Segoe UI",Font.BOLD,36));

        card.add(titleLabel,BorderLayout.NORTH);

        card.add(valueLabel,BorderLayout.CENTER);

        return card;
    }

    public void refreshDashboard(){

        stationsLabel.setText(String.valueOf(network.getStations().size()));

        routesLabel.setText(String.valueOf(network.getRoutesCount()));

        if (network.getStations().isEmpty()) {

            cyclesLabel.setText("No");

        } else {

            cyclesLabel.setText(network.containsCycle() ? "Yes" : "No");

        }
        statusLabel.setText("Ready");

        repaint();
    }

    @Override
    public void onNetworkChanged() {

        refreshDashboard();
    }
}