package Front_End;

import Back_End.Network;
import Back_End.NetworkListener;
import Back_End.Station;
import Front_End.Components.ModernButton;
import Front_End.Components.PageTitle;
import Front_End.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlgorithmsPanel extends JPanel implements NetworkListener {

    private final Network network;
    private JComboBox<String> sourceCombo, destCombo;
    private JTextArea outputArea;

    // الأزرار (لتفعيلها/تعطيلها)
    private ModernButton shortestBtn, cycleBtn, asciiBtn;

    public AlgorithmsPanel(Network network) {
        this.network = network;
        network.addListener(this);

        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new PageTitle("Algorithms"), BorderLayout.NORTH);

        // ---- لوحة التحكم ----
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

        // صف الأزرار
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);

        // تعريف الأزرار (لنتمكن من تفعيلها/تعطيلها)
        shortestBtn = new ModernButton("Shortest Path");
        cycleBtn = new ModernButton("Detect Cycle");
        asciiBtn = new ModernButton("Print ASCII Tree");

        btnPanel.add(shortestBtn);
        btnPanel.add(cycleBtn);
        btnPanel.add(asciiBtn);
        control.add(btnPanel, gbc);

        add(control, BorderLayout.NORTH);

        // ---- منطقة النتائج ----
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Theme.CARD);
        outputArea.setForeground(Theme.TEXT);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.ACCENT));
        add(scroll, BorderLayout.CENTER);

        // ---- ربط الأحداث ----
        shortestBtn.addActionListener(e -> computeShortestPath());
        cycleBtn.addActionListener(e -> detectCycle());
        asciiBtn.addActionListener(e -> printAsciiTree());

        // ---- تحميل البيانات الأولية ----
        refreshCombos();
    }

    // تحديث القوائم وتفعيل الأزرار بناءً على وجود محطات
    private void refreshCombos() {
        ArrayList<Station> stations = network.getStations();
        boolean hasStations = !stations.isEmpty();

        if (hasStations) {
            String[] names = stations.stream().map(s -> s.name).toArray(String[]::new);
            sourceCombo.setModel(new DefaultComboBoxModel<>(names));
            destCombo.setModel(new DefaultComboBoxModel<>(names));
        } else {
            sourceCombo.setModel(new DefaultComboBoxModel<>(new String[]{"No Stations"}));
            destCombo.setModel(new DefaultComboBoxModel<>(new String[]{"No Stations"}));
        }

        // 🔥 تفعيل الأزرار إذا كانت هناك محطات، وإلا تعطيلها
        shortestBtn.setEnabled(hasStations);
        cycleBtn.setEnabled(hasStations);
        asciiBtn.setEnabled(hasStations);

        // إذا كانت الأزرار معطلة، نعرض رسالة توجيهية
        if (!hasStations) {
            outputArea.setText("Please add some stations first.");
        } else {
            // إذا كان النص هو الرسالة السابقة، نمسحه
            if (outputArea.getText().equals("Please add some stations first.")) {
                outputArea.setText("");
            }
        }
    }

    // تنفيذ واجهة NetworkListener (تحديث تلقائي عند تغيير الشبكة)
    @Override
    public void onNetworkChanged() {
        refreshCombos();
    }

    // ----- العمليات -----

    private void computeShortestPath() {
        try {
            String src = (String) sourceCombo.getSelectedItem();
            String dest = (String) destCombo.getSelectedItem();

            // تحقق من صحة الاختيار
            if (src == null || dest == null || src.equals("No Stations") || dest.equals("No Stations")) {
                outputArea.setText("Please select valid source and destination.");
                return;
            }
            if (src.equals(dest)) {
                outputArea.setText("Source and destination cannot be the same.");
                return;
            }

            int distance = network.shortestPath(src, dest);
            outputArea.setText("✅ Shortest distance from " + src + " to " + dest + " = " + distance + " km");

        } catch (Exception ex) {
            outputArea.setText("❌ Error: " + ex.getMessage());
            ex.printStackTrace(); // لتصحيح الأخطاء أثناء التطوير
        }
    }

    private void detectCycle() {
        try {
            boolean hasCycle = network.containsCycle();
            outputArea.setText("🔄 Network contains cycle: " + (hasCycle ? "✅ Yes" : "❌ No"));
        } catch (Exception ex) {
            outputArea.setText("❌ Error while detecting cycle: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void printAsciiTree() {
        try {
            String src = (String) sourceCombo.getSelectedItem();
            if (src == null || src.equals("No Stations")) {
                outputArea.setText("Please select a source station for the tree.");
                return;
            }
            String tree = network.asciiPrint(src);
            outputArea.setText("🌳 ASCII Tree starting from " + src + ":\n\n" + tree);
        } catch (Exception ex) {
            outputArea.setText("❌ Error while printing tree: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}