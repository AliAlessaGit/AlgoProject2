package Front_End;

import Back_End.Network;
import Back_End.NetworkListener;
import Front_End.Theme;
import Front_End.Components.*;
import Back_End.Station;

import Back_End.Edge;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawPanel extends JPanel {
    private final Network network;
    private Map<String, Point> positions;

    public DrawPanel(Network network) {
        this.network = network;
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Network Visualization", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.TEXT);
        add(title, BorderLayout.NORTH);

        // لوحة الرسم المخصصة
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawNetwork(g);
            }
        };
        canvas.setBackground(Theme.BACKGROUND);
        add(canvas, BorderLayout.CENTER);

        // زر تحديث
        ModernButton refreshBtn = new ModernButton("Refresh Layout");
        refreshBtn.addActionListener(e -> {
            calculatePositions();
            canvas.repaint();
        });
        add(refreshBtn, BorderLayout.SOUTH);

        calculatePositions();
    }

    private void calculatePositions() {
        positions = new HashMap<>();
        var stations = network.getStations();
        int size = stations.size();
        if (size == 0) return;

        int width = getWidth() - 100;
        int height = getHeight() - 150;
        if (width <= 0 || height <= 0) {
            width = 600;
            height = 400;
        }

        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 50;

        for (int i = 0; i < size; i++) {
            double angle = 2 * Math.PI * i / size - Math.PI / 2;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            positions.put(stations.get(i).name, new Point(x, y));
        }
    }

    private void drawNetwork(Graphics g) {
        if (positions == null || positions.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // رسم المسارات
        for (Station src : network.getStations()) {
            Point p1 = positions.get(src.name);
            if (p1 == null) continue;
            for (Edge edge : network.getRoutes(src.name)) {
                Point p2 = positions.get(edge.destination.name);
                if (p2 == null) continue;
                g2.setColor(Theme.TEXT_SECONDARY);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                // رسم الوزن في المنتصف
                int mx = (p1.x + p2.x) / 2;
                int my = (p1.y + p2.y) / 2;
                g2.setColor(Theme.ACCENT);
                g2.drawString(String.valueOf(edge.weight), mx, my - 5);
            }
        }

        // رسم المحطات
        for (Station station : network.getStations()) {
            Point p = positions.get(station.name);
            if (p == null) continue;
            g2.setColor(Theme.ACCENT);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Theme.TEXT);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString(station.name, p.x - 20, p.y - 25);
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        calculatePositions();
        repaint();
    }
}