
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

public class ReportsTable extends MyFrame {
    JScrollPane scroll;

    Connection connection;
    Font font = new Font("Sans-serif", Font.PLAIN, 15);

    Reports back;

    public ReportsTable(Reports reports, String sql, String title) {
        back = reports;
        setup(sql, title);
    }

    private void setup(String sql, String title) {
        String dbUrl = "jdbc:mysql://localhost:3306/baraka_db";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, "root",
                    "Mwise2002");
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            ResultSetMetaData metadata = result.getMetaData();

            // using the java table tocreate rows and columns of the information using
            // arrays
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable();
            table.setModel(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFillsViewportHeight(true);
            scroll = new JScrollPane(table);
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            String columnLabels[] = {};

            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                List<String> colList = new ArrayList<String>(
                        Arrays.asList(columnLabels));
                colList.add(metadata.getColumnLabel(i));
                columnLabels = colList.toArray(columnLabels);
            }
            model.setColumnIdentifiers(columnLabels);

            while (result.next()) {
                Object[] row = {};
                for (String label : columnLabels) {
                    List<Object> rowList = new ArrayList<Object>(
                            Arrays.asList(row));
                    rowList.add(result.getObject(label));
                    row = rowList.toArray(row);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JPanel panel = new Panel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(20, 200, 700, 300);

        JLabel headline = new JLabel(title + " Report");
        headline.setFont(new Font("Serif", Font.BOLD, 36));
        headline.setBounds(100, 50, 550, 60);
        headline.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel description = new JLabel("A report on " + title
                + " for Mwanzo Baraka Organization",
                SwingConstants.RIGHT);
        description.setFont(new Font("Serif", Font.PLAIN, 24));
        description.setBounds(100, 130, 550, 30);
        description.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(scroll);

        this.setLayout(null);
        this.add(headline);
        this.add(description);
        this.add(panel);
        JButton back = new JButton("Back");
        back.setBounds(200, 520, 60, 30);
        back.setFont(font);
        back.setBorder(null);
        back.setFocusable(false);
        back.setBackground(Color.red);
        this.add(back);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();

            }

        });

    }

    private void goBack() {
        back.setVisible(true);
        this.dispose();
    }

}
