package Front_End.Dialogs;

import Back_End.Network;
import Back_End.Station;
import Front_End.Theme;
import Front_End.Components.ModernButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddRouteDialog extends JDialog {
    private JComboBox<String> sourceCombo, destCombo;
    private JTextField distanceField;
    private boolean confirmed;
    private final Network network;

    public AddRouteDialog(JFrame parent, Network network) {
        super(parent, "Add Route", true);
        this.network = network;
        buildUI();
    }

    private void buildUI() {
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getParent());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBackground(Theme.BACKGROUND);

        form.add(new JLabel("Source:") {{
            setForeground(Theme.TEXT);
            setFont(Theme.NORMAL_FONT);
        }});
        sourceCombo = new JComboBox<>(getStationNames());
        sourceCombo.setBackground(Theme.CARD);
        sourceCombo.setForeground(Theme.TEXT);
        form.add(sourceCombo);

        form.add(new JLabel("Destination:") {{
            setForeground(Theme.TEXT);
            setFont(Theme.NORMAL_FONT);
        }});
        destCombo = new JComboBox<>(getStationNames());
        destCombo.setBackground(Theme.CARD);
        destCombo.setForeground(Theme.TEXT);
        form.add(destCombo);

        form.add(new JLabel("Distance (km):") {{
            setForeground(Theme.TEXT);
            setFont(Theme.NORMAL_FONT);
        }});
        distanceField = new JTextField();
        distanceField.setBackground(Theme.CARD);
        distanceField.setForeground(Theme.TEXT);
        form.add(distanceField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);
        ModernButton save = new ModernButton("Add");
        ModernButton cancel = new ModernButton("Cancel");
        save.addActionListener(e -> {
            String src = (String) sourceCombo.getSelectedItem();
            String dest = (String) destCombo.getSelectedItem();
            if (src.equals(dest)) {
                JOptionPane.showMessageDialog(this,
                        "Source and destination cannot be the same.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int dist = Integer.parseInt(distanceField.getText().trim());
                if (dist <= 0) throw new NumberFormatException();
                network.addRoute(src, dest, dist);
                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a positive integer for distance.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        buttons.add(save);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);
    }

    private String[] getStationNames() {
        ArrayList<Station> stations = network.getStations();
        String[] names = new String[stations.size()];
        for (int i = 0; i < stations.size(); i++) {
            names[i] = stations.get(i).name;
        }
        return names;
    }

    public boolean isConfirmed() { return confirmed; }
}