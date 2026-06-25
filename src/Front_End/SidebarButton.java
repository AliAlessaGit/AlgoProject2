package Front_End;

import Front_End.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarButton extends JButton {

    public SidebarButton(String text) {

        super(text);

        setFocusPainted(false);

        setBorderPainted(false);

        setContentAreaFilled(false);

        setForeground(Theme.TEXT);

        setFont(new Font("Segoe UI",
                Font.PLAIN,
                15));

        setHorizontalAlignment(SwingConstants.LEFT);

        setBorder(
                new EmptyBorder(
                        12,
                        20,
                        12,
                        10
                )
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setColor(
                getModel().isRollover()
                        ? Theme.TEXT
                        : Theme.SIDEBAR
        );

        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        super.paintComponent(g);
    }
}