package Front_End;
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

        setSize(350,180);

        setLayout(new BorderLayout());

        JPanel center =
                new JPanel(
                        new GridLayout(2,1,5,5));

        center.add(
                new JLabel(
                        "Station Name"));

        nameField =
                new JTextField();

        center.add(nameField);

        add(center,
                BorderLayout.CENTER);

        JButton add =
                new JButton("Add");

        add.addActionListener(
                e -> {

                    confirmed = true;

                    dispose();
                });

        add(add,
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