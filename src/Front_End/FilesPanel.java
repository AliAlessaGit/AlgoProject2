package Front_End;

import Back_End.Network;
import Front_End.Components.ModernButton;
import Front_End.Components.PageTitle;
import Front_End.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FilesPanel extends JPanel {
    private final Network network;
    private JTextArea fileContentArea;

    public FilesPanel(Network network) {
        this.network = network;
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new PageTitle("File Operations"), BorderLayout.NORTH);

        // أزرار العمليات
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        ModernButton importBtn = new ModernButton("Import from File");
        ModernButton exportBtn = new ModernButton("Export to File");
        ModernButton loadSampleBtn = new ModernButton("Load Sample Data");
        btnPanel.add(importBtn);
        btnPanel.add(exportBtn);
        btnPanel.add(loadSampleBtn);
        add(btnPanel, BorderLayout.NORTH);

        // منطقة العرض
        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setBackground(Theme.CARD);
        fileContentArea.setForeground(Theme.TEXT);
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(fileContentArea), BorderLayout.CENTER);

        // الأحداث
        importBtn.addActionListener(e -> importNetwork());
        exportBtn.addActionListener(e -> exportNetwork());
        loadSampleBtn.addActionListener(e -> {
            network.loadSampleData();
            fileContentArea.setText(network.exportNetwork());
        });

        // عرض الشبكة الحالية عند التحميل
        refreshDisplay();
    }

    private void importNetwork() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                network.readNetworkFromFile();
                refreshDisplay();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error reading file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportNetwork() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                network.writeToFile();
                JOptionPane.showMessageDialog(this,
                        "Network exported successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error writing file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshDisplay() {
        fileContentArea.setText(network.exportNetwork());
    }
}