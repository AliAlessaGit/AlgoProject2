package Front_End.Components;

import Front_End.Theme;

import javax.swing.*;

public class PageTitle extends JLabel {

    public PageTitle(String text) {

        super(text);

        setFont(Theme.TITLE_FONT);

        setForeground(Theme.TEXT);
    }
}