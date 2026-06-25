package Front_End.Components;

import Front_End.Theme;

import javax.swing.*;
import java.awt.*;

public class CustomCard extends JPanel {

    public CustomCard() {

        setOpaque(false);

        setLayout(new BorderLayout());

        setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Theme.CARD);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                20,
                20);

        g2.dispose();

        super.paintComponent(g);
    }
}