package Front_End.Dialogs;

import Front_End.Theme;
import Front_End.Components.ModernButton;
import Back_End.Network;
import Back_End.Station;

import javax.swing.*;
import java.awt.*;

public class EditStationDialog extends JDialog {
    private JTextField nameField;
    private JTextField symbolField;
    private boolean confirmed;
    private final Station oldStation;
    private final Network network;
    private String newName;
    private String newSymbol;

    public EditStationDialog(JFrame parent, Network network, Station station) {
        super(parent, "Edit Station", true);
        this.oldStation = station;
        this.network = network;
        buildUI();
    }

    private void buildUI() {
        setSize(420, 280);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getParent());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBackground(Theme.BACKGROUND);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // حقل الاسم
        JLabel nameLabel = new JLabel("Station Name:");
        nameLabel.setForeground(Theme.TEXT);
        nameLabel.setFont(Theme.NORMAL_FONT);
        nameField = new JTextField(oldStation.name);
        nameField.setFont(Theme.NORMAL_FONT);
        nameField.setBackground(Theme.CARD);
        nameField.setForeground(Theme.TEXT);
        nameField.setBorder(BorderFactory.createLineBorder(Theme.ACCENT));

        // حقل الرمز
        JLabel symbolLabel = new JLabel("Symbol (Code):");
        symbolLabel.setForeground(Theme.TEXT);
        symbolLabel.setFont(Theme.NORMAL_FONT);
        symbolField = new JTextField(oldStation.symbol != null ? oldStation.symbol : "");
        symbolField.setFont(Theme.NORMAL_FONT);
        symbolField.setBackground(Theme.CARD);
        symbolField.setForeground(Theme.TEXT);
        symbolField.setBorder(BorderFactory.createLineBorder(Theme.ACCENT));

        form.add(nameLabel);
        form.add(nameField);
        form.add(symbolLabel);
        form.add(symbolField);

        add(form, BorderLayout.CENTER);

        // الأزرار
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);
        ModernButton saveBtn = new ModernButton("Save");
        ModernButton cancelBtn = new ModernButton("Cancel");

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String symbol = symbolField.getText().trim();

            // تحقق من أن الاسم غير فارغ
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Station name cannot be empty!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // تحقق من أن الرمز غير فارغ (اختياري، يمكنك السماح بتركه فارغاً)
            if (symbol.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Symbol cannot be empty!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // تحقق من عدم تكرار الاسم (باستثناء نفس المحطة)
            if (!name.equals(oldStation.name)) {
                for (Station st : network.getStations()) {
                    if (st.equals(oldStation)) continue;
                    if (st.name.equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(this,
                                "Station name '" + name + "' already exists!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // تحقق من عدم تكرار الرمز (باستثناء نفس المحطة)
            if (!symbol.equals(oldStation.symbol)) {
                for (Station st : network.getStations()) {
                    if (st.equals(oldStation)) continue;
                    if (st.symbol != null && st.symbol.equalsIgnoreCase(symbol)) {
                        JOptionPane.showMessageDialog(this,
                                "Symbol '" + symbol + "' already used by another station!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            newName = name;
            newSymbol = symbol;
            confirmed = true;
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttons.add(saveBtn);
        buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveBtn);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getNewName() { return newName; }
    public String getNewSymbol() { return newSymbol; }
}