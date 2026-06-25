package Front_End;

import Back_End.Network;
import Front_End.Pages.DashboardPanel;

import javax.swing.*;
import java.awt.*;

public class NetworkFrame extends JFrame {

    private Network network;

    private CardLayout cardLayout;

    private JPanel contentPanel;

    public NetworkFrame(Network network) {

        this.network = network;

        initializeFrame();

        createSidebar();

        createContent();
    }
    private void initializeFrame() {

        setTitle(
                "Railway Network Manager");

        setSize(1400, 850);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                Theme.BACKGROUND);
    }

    private void createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(230, 0));

        sidebar.setBackground(
                Theme.SIDEBAR);

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS));

        JLabel logo =
                new JLabel(
                        "NETWORK");

        logo.setForeground(
                Theme.ACCENT);

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24));

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        logo.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                30,
                                0,
                                30,
                                0));

        sidebar.add(logo);

        SidebarButton dashboard =
                new SidebarButton("Dashboard");

        SidebarButton stations =
                new SidebarButton("Stations");

        SidebarButton routes =
                new SidebarButton("Routes");

        SidebarButton algorithms =
                new SidebarButton("Algorithms");

        SidebarButton files =
                new SidebarButton("Files");

        SidebarButton draw =
                new SidebarButton("Draw Network");

        sidebar.add(dashboard);
        sidebar.add(stations);
        sidebar.add(routes);
        sidebar.add(algorithms);
        sidebar.add(files);
        sidebar.add(draw);

        add(sidebar, BorderLayout.WEST);

        dashboard.addActionListener(
                e -> showCard("dashboard"));

        stations.addActionListener(
                e -> showCard("stations"));

        routes.addActionListener(
                e -> showCard("routes"));

        algorithms.addActionListener(
                e -> showCard("algorithms"));

        files.addActionListener(
                e -> showCard("files"));

        draw.addActionListener(
                e -> showCard("draw"));
    }

    private void createContent() {

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        contentPanel.add(
                new DashboardPanel(),
                "dashboard");

        contentPanel.add(
                new StationsPanel(network),
                "stations");

        contentPanel.add(
                new RoutesPanel(),
                "routes");

        contentPanel.add(
                new AlgorithmsPanel(),
                "algorithms");

        contentPanel.add(
                new FilesPanel(),
                "files");

        contentPanel.add(
                new DrawPanel(),
                "draw");

        add(contentPanel,
                BorderLayout.CENTER);

        cardLayout.show(
                contentPanel,
                "dashboard");
    }

    private void showCard(
            String cardName) {

        cardLayout.show(
                contentPanel,
                cardName);
    }
}