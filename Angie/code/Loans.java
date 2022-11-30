
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.*;

public class Loans extends MyFrame {
    PreparedStatement sqlst1, sqlst2, sqlst3;// runs sql
    Connection dbConnect;
    int repayment = 0;
    int amount = 0;
    int loan;
    JTextField numberText, amountText;
    Boolean group;
    double payableAmount, interest;
    String grp_sql = "SELECT amount FROM contributions WHERE group_name=?";
    String ind_sql = "SELECT amount FROM contributions WHERE member_id=?";
    String member_group_sql = "SELECT group_name FROM members WHERE id_number=?";
    String group_sql = " insert into loans (group_name,loan_amount,repay_period) values(?,?,?)";
    String individual_sql = "insert into loans (id_num,loan_amount,repay_period) values(?,?,?)";

    PreparedStatement state, Ind_statement, memb_state;

    JLabel interestLabel, error;
    JLabel amountLabel, penaltyLabel;
    JTextField repayText;

    public Loans() {
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

        JLabel title = new JLabel("LOANS");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setBounds(10, 20, 300, 30);
        form.add(title);

        JLabel number = new JLabel("National ID/Group Name");
        number.setBounds(10, 70, 165, 30);
        number.setFont(font);
        form.add(number);

        numberText = new JTextField();
        numberText.setBounds(10, 100, 165, 25);
        numberText.setBorder(null);
        form.add(numberText);

        // amount of loan requested by the group or individual or member
        JLabel title1 = new JLabel("How much do you need?");
        title1.setFont(font);
        title1.setBounds(10, 130, 200, 30);
        form.add(title1);

        amountText = new JTextField();
        amountText.setBounds(10, 160, 165, 30);
        amountText.setBorder(null);
        form.add(amountText);

        // the time that will be taken by the group/member/individual to repay the loan
        JLabel repaymentTime = new JLabel("Repayment Period (Months)"); // <=4
        repaymentTime.setBounds(10, 190, 300, 25);
        repaymentTime.setFont(font);
        form.add(repaymentTime);

        repayText = new JTextField();
        repayText.setBounds(10, 215, 165, 30);
        repayText.setBorder(null);
        repayText.setFont(font);
        form.add(repayText);

        JButton computeLoan = new JButton("LOAN");
        computeLoan.setFont(font);
        computeLoan.setBounds(10, 260, 100, 30);
        computeLoan.setFocusable(false);
        computeLoan.setBorder(null);
        computeLoan.setBackground(Color.red);
        form.add(computeLoan);
        computeLoan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int id_num = Integer.parseInt(numberText.getText());
                    int loan_amount = Integer.parseInt(amountText.getText());
                    int repay_period = Integer.parseInt(repayText.getText());

                    state = dbConnect.prepareStatement(ind_sql);
                    state.setString(1, String.valueOf(id_num));
                    ResultSet result = state.executeQuery();
                    int output = 0;
                    while (result.next()) {
                        output += result.getInt("amount");
                    }

                    PreparedStatement member_grp = dbConnect.prepareStatement(member_group_sql);
                    member_grp.setString(1, String.valueOf(id_num));
                    ResultSet groupResult = member_grp.executeQuery();
                    String memberGroup = null;
                    if (groupResult.next()) {
                        memberGroup = groupResult.getString("group_name");
                    }
                    Boolean isInGroup = memberGroup != null;

                    // calculating the maximum amount of the loan to be borrowed and displaying an
                    // error text if the loan borrowed is greater then loan limit
                    int Availableloan;
                    int maxPeriod;
                    if (isInGroup) {
                        Availableloan = output * 4;
                        maxPeriod = 4;
                    } else {
                        Availableloan = output * 3;
                        maxPeriod = 3;
                    }

                    if (loan_amount > Availableloan) {
                        error.setText("Maximum loan is " + String.valueOf(Availableloan));
                        return;
                    }
                    memb_state = dbConnect.prepareStatement(individual_sql);
                    memb_state.setString(1, String.valueOf(id_num));
                    memb_state.setInt(2, loan_amount);

                    // displaying an error text if the repayment period entered by the user is
                    // greater than the maximum period
                    if (repay_period > maxPeriod * 12) {
                        error.setText("Maximum period is " + String.valueOf(maxPeriod) + "years");
                        return;
                    }
                    memb_state.setInt(3, repay_period);
                    memb_state.executeUpdate();
                    confirmLoan();

                } catch (SQLException ex) {
                    Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("SQL is Bad!!!" + ex.getMessage());
                } catch (NumberFormatException ex) {

                    // if the information entered in the numberText textfield is not in number
                    // format it means that the person is in a group
                    // hence the method group loan will be executed
                    group_loan();

                }
            }

        });

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

        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 290, 300, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(error);

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void goBack() {
        JFrame Home = new Home();
        Home.setVisible(true);
        this.dispose();
    }

    private void group_loan() {

        // if the it is a group the following statements will be executed for the group
        // in the database

        String group_name = numberText.getText();
        int loan_amount = Integer.parseInt(amountText.getText());
        int repay_period = Integer.parseInt(repayText.getText());

        try {
            sqlst2 = dbConnect.prepareStatement(group_sql);
            sqlst2.setString(1, group_name);

            PreparedStatement sqlst1 = dbConnect.prepareStatement(grp_sql);
            sqlst1.setString(1, group_name);
            ResultSet result = sqlst1.executeQuery();
            int output = 0;
            while (result.next()) {
                output += result.getInt("amount");
            }

            int Availableloan = output * 3;

            // calculating the loan limit

            if (loan_amount > Availableloan) {
                error.setText("Maximum loan is " + String.valueOf(Availableloan));
                return;
            }
            sqlst2.setInt(2, loan_amount);
            // calcualting the loan limit
            if (repay_period > 60) {
                error.setText("Maximum period is 5 years");
                return;
            }
            sqlst2.setInt(3, repay_period);
            sqlst2.executeUpdate();
            confirmLoan();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void confirmLoan() {
        try {
            PreparedStatement statement = dbConnect
                    .prepareStatement("select loan_code from loans where loan_code=LAST_INSERT_ID()");
            ResultSet result = statement.executeQuery();
            int id = 0;
            if (result.next()) {
                id = result.getInt("loan_code");
            }

            JOptionPane.showMessageDialog(this, "Loan requested has been issued. The loan ID is " + String.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        goBack();
        ;
    }

}