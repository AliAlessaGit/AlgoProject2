package Front_End;

import Back_End.Network;
import Front_End.Components.ModernButton;
import Front_End.Components.PageTitle;
import Front_End.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesPanel extends JPanel {
    private final Network network;
    private JTextArea fileContentArea;

    public FilesPanel(Network network) {
        this.network = network;
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new PageTitle("File Operations"), BorderLayout.NORTH);

        // ---- أزرار العمليات ----
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        ModernButton importBtn = new ModernButton("📂 Import from File");
        ModernButton exportBtn = new ModernButton("💾 Export to File");
        ModernButton loadSampleBtn = new ModernButton("📊 Load Sample Data");
        btnPanel.add(importBtn);
        btnPanel.add(exportBtn);
        btnPanel.add(loadSampleBtn);
        add(btnPanel, BorderLayout.NORTH);

        // ---- منطقة العرض ----
        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setBackground(Theme.CARD);
        fileContentArea.setForeground(Theme.TEXT);
        fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(fileContentArea), BorderLayout.CENTER);

        // ---- الأحداث ----
        importBtn.addActionListener(e -> importNetworkFromFile());
        exportBtn.addActionListener(e -> exportNetworkToFile());
        loadSampleBtn.addActionListener(e -> {
            network.loadSampleData();
            refreshDisplay();
            JOptionPane.showMessageDialog(this,
                    "Sample data loaded successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // عرض الشبكة الحالية عند التحميل
        refreshDisplay();

        // تحديث عند تغيير الشبكة
        network.addListener(() -> refreshDisplay());
    }

    // استيراد من ملف (يختار المستخدم المسار)
    private void importNetworkFromFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Network File to Import");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            try {
                // قراءة الملف وعرض محتواه في المنطقة النصية
                String content = Files.readString(Paths.get(path));
                fileContentArea.setText(content);

                // استيراد الشبكة
                network.importNetwork(content);
                JOptionPane.showMessageDialog(this,
                        "Network imported successfully from:\n" + path,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error reading file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error importing network: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // تصدير إلى ملف (يختار المستخدم المسار)
    private void exportNetworkToFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Network As");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setSelectedFile(new java.io.File("network_export.txt"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            try {
                network.writeToFile(path);
                JOptionPane.showMessageDialog(this,
                        "Network exported successfully to:\n" + path,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // عرض المحتوى المصدر في منطقة النص
                String content = network.exportNetwork();
                fileContentArea.setText(content);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error writing file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // تحديث عرض المحتوى
    private void refreshDisplay() {
        String content = network.exportNetwork();
        fileContentArea.setText(content.isEmpty() ? "Network is empty." : content);
    }
}