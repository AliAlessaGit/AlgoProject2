package Front_End.Pages;

import Front_End.Theme;
import Front_End.Components.*;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {

        setBackground(
                Theme.BACKGROUND);

        setLayout(
                new BorderLayout(
                        20,
                        20));

        setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        25,
                        25,
                        25));

        add(
                new PageTitle(
                        "Dashboard"),
                BorderLayout.NORTH);

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                20,
                                20));

        cards.setOpaque(false);

        cards.add(
                createInfoCard(
                        "Stations",
                        "0"));

        cards.add(
                createInfoCard(
                        "Routes",
                        "0"));

        cards.add(
                createInfoCard(
                        "Cycles",
                        "Unknown"));

        cards.add(
                createInfoCard(
                        "Status",
                        "Ready"));

        add(cards,
                BorderLayout.CENTER);
    }

    private CustomCard createInfoCard(
            String title,
            String value) {

        CustomCard card =
                new CustomCard();

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                Theme.TEXT_SECONDARY);

        titleLabel.setFont(
                Theme.SUBTITLE_FONT);

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(
                Theme.ACCENT);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        36));

        card.add(
                titleLabel,
                BorderLayout.NORTH);

        card.add(
                valueLabel,
                BorderLayout.CENTER);

        return card;
    }
}