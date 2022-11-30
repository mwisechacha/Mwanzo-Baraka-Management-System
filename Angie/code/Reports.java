
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Reports extends MyFrame {
    Connection dbConnect;
    JLabel totalIncome, organizationIncome;

    public Reports() {
        String dbUrl = "jdbc:mysql://localhost:3306/baraka_db";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnect = DriverManager.getConnection(dbUrl, "root",
                    "Mwise2002");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // layout
        Font font = new Font("Sans-serif", Font.PLAIN, 15);
        JPanel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2, 50, 0));
        panel.setBackground(Color.GRAY);

        JPanel form = new JPanel();
        form.setBackground(Color.GRAY);
        form.setLayout(null);
        form.setBackground(null);

        // Form

        //
        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Ink Free", Font.BOLD, 35));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(title);

        // generation of reports for the organization income, total income
        int income = getIncome();
        double orgIncome = income * 0.4;

        totalIncome = new JLabel("<html>Total Income: <b>sh. " + String.valueOf(income) + "</b></html>");
        totalIncome.setFont(new Font("Serif", Font.PLAIN, 24));
        totalIncome.setBounds(0, 150, 600, 30);
        form.add(totalIncome);

        organizationIncome = new JLabel(
                "<html>Organization Income: <b>sh. " + String.valueOf(orgIncome) + "</b></html>");
        organizationIncome.setFont(new Font("Serif", Font.PLAIN, 24));
        organizationIncome.setBounds(0, 200, 600, 30);
        form.add(organizationIncome);

        // the buttons create a table for the
        // members,groups,contribution,loans,repayments,dividends
        JButton membersButton = new JButton("Members");
        membersButton.setBounds(0, 270, 225, 50);
        membersButton.setBorder(null);
        membersButton.setBackground(Color.red);
        form.add(membersButton);

        JButton groupsButton = new JButton("Groups");
        groupsButton.setBounds(250, 270, 225, 50);
        groupsButton.setBackground(Color.red);
        groupsButton.setBorder(null);
        form.add(groupsButton);

        JButton contributionsButton = new JButton("Contributions");
        contributionsButton.setBounds(0, 340, 225, 50);
        contributionsButton.setBorder(null);
        contributionsButton.setBackground(Color.red);
        form.add(contributionsButton);

        JButton loansButton = new JButton("Loans");
        loansButton.setBounds(250, 340, 225, 50);
        loansButton.setBorder(null);
        loansButton.setBackground(Color.red);
        form.add(loansButton);

        JButton repaymentsButton = new JButton("Repayments");
        repaymentsButton.setBounds(0, 410, 225, 50);
        repaymentsButton.setBorder(null);
        repaymentsButton.setBackground(Color.red);
        form.add(repaymentsButton);

        JButton dividendsButton = new JButton("Dividends");
        dividendsButton.setBounds(250, 410, 225, 50);
        dividendsButton.setBorder(null);
        dividendsButton.setBackground(Color.red);
        form.add(dividendsButton);

        // getting the information form the sql and diplaying them in the reports table
        // using a switch case
        JButton buttons[] = { membersButton, groupsButton, contributionsButton, loansButton, repaymentsButton,
                dividendsButton };
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String sql = null;
                    switch (button.getText()) {
                        case "Members":
                            sql = "SELECT * FROM members;";
                            break;
                        case "Groups":
                            sql = "SELECT group_name,reg_fee FROM group_details;";
                            break;
                        case "Contributions":
                            sql = "SELECT member_id,group_name,amount FROM contributions";
                            break;
                        case "Loans":
                            sql = "SELECT * FROM loans";
                            break;
                        case "Repayments":
                            sql = "SELECT loan_id,installment,amount,interest,penalties FROM repayments";
                            break;
                        case "Dividends":
                            sql = "SELECT member.id as member_id, member.grp as group_name, member.n as contribution, round((member.n / total.n * revenue.dividend), 2)  as dividend from (select member_id as id, group_name as grp, sum(amount) as n from contributions group by member_id, group_name)member join (select sum(amount) as n from contributions)total join(SELECT (SUM(interest)+SUM(penalties))*0.6 AS dividend FROM repayments)revenue;";
                            break;
                        default:
                            break;
                    }

                    if (sql != null) {
                        display(sql, button.getText());
                    }
                }

            });
        }

        JButton back = new JButton("Back");
        back.setBounds(200, 500, 60, 30);
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

        panel.setBorder(new EmptyBorder(20, 100, 20, 0));
        panel.add(form);

        this.add(panel);
    }

    private int getIncome() {
        // summation of the income from the sql..which is...
        String sql = "SELECT (SUM(interest)+SUM(penalties)) AS income FROM repayments";
        try {
            PreparedStatement statement = dbConnect.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("income");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    private void goBack() {
        JFrame Home = new Home();
        Home.setVisible(true);
        this.dispose();
    }

    // opens the reports table for each button,sets the title and the sql statements
    private void display(String sql, String title) {
        new ReportsTable(this, sql, title).setVisible(true);
        this.setVisible(false);
    }
}
