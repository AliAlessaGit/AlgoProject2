package Front_End.Components;

import Front_End.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModernButton extends JButton {

    public ModernButton(String text) {

        super(text);

        setFocusPainted(false);

        setBorderPainted(false);

        setForeground(Color.WHITE);

        setBackground(Theme.ACCENT);

        setFont(Theme.NORMAL_FONT);

        setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR));

        setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20));
    }
}