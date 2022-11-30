
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.*;

public class repayments extends MyFrame {

    Connection dbConnect;

    JTextField loanIdText;
    JLabel installmentsLabel, amountLabel, penaltyLabel, DueLabel;
    JLabel error;

    int amount;
    Double interest = 0.0;
    int penalty = 0;
    int installment = 1;

    public repayments() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localHost:3306/baraka_db";
            dbConnect = DriverManager.getConnection(dbURL, "root", "Mwise2002");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class Not Found, check the JAR");
        } catch (SQLException ex) {
            Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL is Bad!!!" + ex.getMessage());
        }

        Font font = new Font("Sans-serif", Font.PLAIN, 15);
        // layout
        JPanel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2, 50, 0));
        panel.setBackground(Color.GRAY);

        // logo
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("logo_small.png"));

        JPanel form = new JPanel();
        form.setBackground(Color.GRAY);
        form.setLayout(null);
        form.setBackground(null);

        JLabel title = new JLabel(" REPAYMENTS ");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setBounds(10, 20, 300, 30);
        form.add(title);

        JLabel loanId = new JLabel("Loan ID/Group name");
        loanId.setBounds(10, 70, 165, 30);
        loanId.setFont(font);
        form.add(loanId);

        loanIdText = new JTextField();
        loanIdText.setBounds(10, 100, 165, 25);
        loanIdText.setBorder(null);
        form.add(loanIdText);

        // sets an actionListener that puts information in the database
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 135, 100, 30);
        confirmButton.setFont(font);
        confirmButton.setFocusable(false);
        confirmButton.setBorder(null);
        confirmButton.setBackground(Color.red);
        form.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String loan_id = loanIdText.getText();
                try {
                    PreparedStatement statement = dbConnect.prepareStatement("select * from loans where loan_code=?");
                    statement.setString(1, loan_id);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        amount = result.getInt("loan_amount") / result.getInt("repay_period");

                        String member_id = result.getString("id_num");
                        String group_name = result.getString("group_name");
                        Boolean isInGroup = false;
                        Boolean group = group_name != null;

                        if (member_id != null) {
                            PreparedStatement memberStatement = dbConnect
                                    .prepareStatement("select group_name from members where id_number=?");
                            memberStatement.setString(1, member_id);
                            ResultSet memberResult = memberStatement.executeQuery();
                            if (memberResult.next()) {
                                isInGroup = memberResult.getString("group_name") != null;
                            }
                        }
                        // calculating the interest to be paid
                        if (group) {
                            interest = 0.8 * amount;
                        } else if (isInGroup) {
                            interest = 1.0 * amount;
                        } else {
                            interest = 1.2 * amount;
                        }
                        String time = result.getString("time");

                        // installments in which theloan is being rapaid
                        PreparedStatement repaymentStatement = dbConnect
                                .prepareStatement(
                                        "SELECT installment,time FROM repayments WHERE loan_id=? ORDER BY time ASC LIMIT 1");
                        repaymentStatement.setString(1, loan_id);
                        ResultSet repaymentResult = repaymentStatement.executeQuery();
                        if (repaymentResult.next()) {
                            installment = repaymentResult.getInt("installment") + 1;
                            time = repaymentResult.getString("time");
                        }

                        // penalties
                        LocalDate lastDate = LocalDate.parse(time);
                        LocalDate dueDate = lastDate.plusMonths(1);

                        // penalty is charged if the date when the first installment is to be paid has
                        // been past
                        if (LocalDate.now().isAfter(dueDate)) {
                            // 10% of the amount that was to be paid in that month
                            Double penaltyDouble = amount * 0.1;
                            penalty = penaltyDouble.intValue();
                            penaltyLabel.setText("Penalty: " + String.valueOf(penalty));
                        }

                        installmentsLabel.setText("Installment: " + String.valueOf(installment));
                        DueLabel.setText("Due: " + String.valueOf(dueDate));
                        amountLabel.setText("Amount: " + String.valueOf(amount + interest));
                    } else {
                        error.setText("Enter a valid loan ID");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // setting the test for the installment,due date and the penalty charged
        installmentsLabel = new JLabel("Installment: ");
        installmentsLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        installmentsLabel.setBounds(10, 165, 250, 30);
        form.add(installmentsLabel);

        DueLabel = new JLabel("Due:");
        DueLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        DueLabel.setBounds(10, 195, 165, 30);
        form.add(DueLabel);

        amountLabel = new JLabel("Amount: ");
        amountLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        amountLabel.setBounds(10, 225, 165, 30);
        form.add(amountLabel);

        penaltyLabel = new JLabel("Penalty: N/A");
        penaltyLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        penaltyLabel.setBounds(10, 255, 165, 30);
        form.add(penaltyLabel);

        JButton ComputeInterest = new JButton("Complete");
        ComputeInterest.setBounds(10, 290, 100, 30);
        ComputeInterest.setFont(font);
        ComputeInterest.setFocusable(false);
        ComputeInterest.setBorder(null);
        ComputeInterest.setBackground(Color.red);
        form.add(ComputeInterest);
        ComputeInterest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String loan_id = loanIdText.getText();
                try {
                    PreparedStatement state = dbConnect
                            .prepareStatement(
                                    "insert into repayments (loan_id,installment,amount,interest,penalties) values(?,?,?,?,?)");
                    state.setString(1, loan_id);
                    state.setInt(2, installment);
                    state.setInt(3, amount);
                    state.setInt(4, interest.intValue());
                    if (penalty > 0) {
                        state.setInt(5, penalty);
                    } else {
                        state.setNull(5, 0);
                    }
                    state.executeUpdate();
                    goBack();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 350, 300, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(error);

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

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void goBack() {
        JFrame Home_Group = new Home();
        Home_Group.setVisible(true);
        this.dispose();
    }

}
