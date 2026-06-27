package Front_End;

import Back_End.Network;
import Back_End.Station;
import Front_End.Components.ModernButton;
import Front_End.Components.PageTitle;
import Front_End.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlgorithmsPanel extends JPanel {
    private final Network network;
    private JComboBox<String> sourceCombo, destCombo;
    private JTextArea outputArea;

    public AlgorithmsPanel(Network network) {
        this.network = network;
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new PageTitle("Algorithms"), BorderLayout.NORTH);

        // لوحة التحكم
        JPanel control = new JPanel(new GridBagLayout());
        control.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // صف المصدر والوجهة
        gbc.gridx = 0; gbc.gridy = 0;
        control.add(new JLabel("Source:") {{ setForeground(Theme.TEXT); }}, gbc);
        gbc.gridx = 1;
        sourceCombo = new JComboBox<>();
        sourceCombo.setBackground(Theme.CARD);
        sourceCombo.setForeground(Theme.TEXT);
        control.add(sourceCombo, gbc);

        gbc.gridx = 2;
        control.add(new JLabel("Destination:") {{ setForeground(Theme.TEXT); }}, gbc);
        gbc.gridx = 3;
        destCombo = new JComboBox<>();
        destCombo.setBackground(Theme.CARD);
        destCombo.setForeground(Theme.TEXT);
        control.add(destCombo, gbc);

        // أزرار الخوارزميات
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);

        ModernButton shortestBtn = new ModernButton("Shortest Path");
        ModernButton cycleBtn = new ModernButton("Detect Cycle");
        ModernButton asciiBtn = new ModernButton("Print ASCII Tree");

        btnPanel.add(shortestBtn);
        btnPanel.add(cycleBtn);
        btnPanel.add(asciiBtn);
        control.add(btnPanel, gbc);

        add(control, BorderLayout.NORTH);

        // منطقة النتائج
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Theme.CARD);
        outputArea.setForeground(Theme.TEXT);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.ACCENT));
        add(scroll, BorderLayout.CENTER);

        // الأحداث
        shortestBtn.addActionListener(e -> computeShortestPath());
        cycleBtn.addActionListener(e -> detectCycle());
        asciiBtn.addActionListener(e -> printAsciiTree());

        refreshCombos();
    }

    private void refreshCombos() {
        ArrayList<Station> stations = network.getStations();
        String[] names = stations.stream().map(s -> s.name).toArray(String[]::new);
        sourceCombo.setModel(new DefaultComboBoxModel<>(names));
        destCombo.setModel(new DefaultComboBoxModel<>(names));
    }

    private void computeShortestPath() {
        String src = (String) sourceCombo.getSelectedItem();
        String dest = (String) destCombo.getSelectedItem();
        if (src == null || dest == null) return;
        try {
            int dist = network.shortestPath(src, dest);
            outputArea.setText("Shortest distance from " + src + " to " + dest + " = " + dist + " km");
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void detectCycle() {
        boolean hasCycle = network.containsCycle();
        outputArea.setText("Network contains cycle: " + (hasCycle ? "Yes" : "No"));
    }

    private void printAsciiTree() {
        String src = (String) sourceCombo.getSelectedItem();
        if (src == null) return;
        String tree = network.asciiPrint(src);
        outputArea.setText(tree);
    }
}