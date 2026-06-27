package Front_End;

import Back_End.Edge;
import Back_End.Network;
import Back_End.Station;
import Front_End.Components.ModernButton;
import Front_End.Components.PageTitle;
import Front_End.Dialogs.AddRouteDialog; // سننشئه لاحقاً
import Front_End.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class RoutesPanel extends JPanel {
    private final Network network;
    private JTable routesTable;
    private DefaultTableModel tableModel;

    public RoutesPanel(Network network) {
        this.network = network;
        setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // العنوان
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(new PageTitle("Routes"), BorderLayout.WEST);

        ModernButton addBtn = new ModernButton("+ Add Route");
        top.add(addBtn, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // الجدول
        String[] columns = {"Source", "Destination", "Distance (km)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 2; // نسمح بتعديل الوزن فقط
            }
        };
        routesTable = new JTable(tableModel);
        routesTable.setBackground(Theme.CARD);
        routesTable.setForeground(Theme.TEXT);
        routesTable.setGridColor(Theme.TEXT_SECONDARY);
        routesTable.setFont(Theme.NORMAL_FONT);
        routesTable.getTableHeader().setBackground(Theme.SIDEBAR);
        routesTable.getTableHeader().setForeground(Theme.TEXT);
        routesTable.getTableHeader().setFont(Theme.SUBTITLE_FONT);

        // زر الحذف
        ModernButton deleteBtn = new ModernButton("Delete Selected");
        deleteBtn.addActionListener(e -> deleteSelectedRoute());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.add(deleteBtn);
        add(bottom, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(routesTable);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // إضافة مسار
        addBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddRouteDialog dialog = new AddRouteDialog(frame, network);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                refreshTable();
            }
        });

        // تحديث الوزن عند التعديل المباشر في الجدول
        routesTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 2 && row >= 0) {
                String source = (String) tableModel.getValueAt(row, 0);
                String dest = (String) tableModel.getValueAt(row, 1);
                try {
                    int newWeight = Integer.parseInt((String) tableModel.getValueAt(row, 2));
                    network.updateRoute(source, dest, newWeight);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid distance! Must be a number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    refreshTable(); // إعادة تحميل القيمة القديمة
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Station src : network.getStations()) {
            for (Edge edge : network.getRoutes(src.name)) {
                tableModel.addRow(new Object[]{
                        src.name,
                        edge.destination.name,
                        edge.weight
                });
            }
        }
    }

    private void deleteSelectedRoute() {
        int row = routesTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a route to delete.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String source = (String) tableModel.getValueAt(row, 0);
        String dest = (String) tableModel.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete route from " + source + " to " + dest + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            network.removeRoute(source, dest);
            refreshTable();
        }
    }
}