package Front_End.Dialogs;

import Front_End.Theme;
import Front_End.Components.ModernButton;

import javax.swing.*;
import java.awt.*;

public class AddStationDialog extends JDialog {

    private JTextField nameField;

    private boolean confirmed;

    public AddStationDialog(JFrame parent) {

        super(parent,
                "Add Station",
                true);

        buildUI();
    }

    private void buildUI() {

        setSize(400,220);

        setLayout(
                new BorderLayout(
                        10,
                        10));

        JPanel center =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                10,
                                10));

        center.add(
                new JLabel(
                        "Station Name"));

        nameField =
                new JTextField();

        center.add(nameField);

        add(center,
                BorderLayout.CENTER);

        ModernButton addButton =
                new ModernButton("Add");

        addButton.addActionListener(
                e -> {

                    confirmed = true;

                    dispose();
                });

        add(addButton,
                BorderLayout.SOUTH);

        setLocationRelativeTo(
                getParent());
    }

    public boolean isConfirmed() {

        return confirmed;
    }

    public String getStationName() {

        return nameField.getText();
    }
}