package Front_End.Dialogs;

import Front_End.Theme;
import Front_End.Components.ModernButton;
import Back_End.Network;
import Back_End.Station;

import javax.swing.*;
import java.awt.*;

public class EditStationDialog extends JDialog {
    private JTextField nameField;
    private boolean confirmed;
    private final Station oldStation;
    private final Network network;

    public EditStationDialog(JFrame parent, Network network, Station station) {
        super(parent, "Edit Station", true);
        this.oldStation = station;
        this.network = network;
        buildUI();
    }

    private void buildUI() {
        setSize(400, 220);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getParent());

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        center.setBackground(Theme.BACKGROUND);
        center.add(new JLabel("Station Name") {{
            setForeground(Theme.TEXT);
            setFont(Theme.NORMAL_FONT);
        }});

        nameField = new JTextField(oldStation.name);
        nameField.setFont(Theme.NORMAL_FONT);
        nameField.setBackground(Theme.CARD);
        nameField.setForeground(Theme.TEXT);
        nameField.setBorder(BorderFactory.createLineBorder(Theme.ACCENT));
        center.add(nameField);

        add(center, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        ModernButton saveBtn = new ModernButton("Save");
        ModernButton cancelBtn = new ModernButton("Cancel");

        saveBtn.addActionListener(e -> {
            String newName = nameField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be empty!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newName.equals(oldStation.name) && network.stationExists(newName)) {
                JOptionPane.showMessageDialog(this,
                        "Station '" + newName + "' already exists!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            confirmed = true;
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Enter key triggers save
        getRootPane().setDefaultButton(saveBtn);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getNewName() {
        return nameField.getText().trim();
    }
}