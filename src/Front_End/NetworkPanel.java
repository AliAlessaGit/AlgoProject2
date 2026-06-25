package Front_End;

import Back_End.Network;

import javax.swing.*;
import java.awt.*;

public class NetworkPanel extends JPanel {

    private Network network;

    public NetworkPanel(Network network) {

        this.network = network;

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g;

        g2.drawString(
                "Back_End.Network Drawing Area",
                20,
                20
        );
    }

    public void refresh() {

        repaint();
    }
}