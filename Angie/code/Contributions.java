
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.*;

public class Contributions extends MyFrame {
    PreparedStatement sqlst;// runs sql
    String groupName;
    ResultSet result; // holds output from sql
    String SQL_grp = "SELECT group_name FROM members WHERE id_number=?";
    PreparedStatement state, statement;
    JLabel error;
    Boolean group = false;

    public Contributions() {
        Font font = new Font("Sans-Serif", Font.BOLD, 15);
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

        JLabel title = new JLabel("MONTHLY CONTRIBUTIONS");
        title.setFont(font);
        title.setBounds(10, 100, 250, 30);
        form.add(title);

        // id number of an individual is entered or
        // group name of the registered group
        JLabel number = new JLabel("National ID/Group Name");
        number.setBounds(10, 150, 165, 30);
        number.setFont(font);
        form.add(number);

        JTextField numberText = new JTextField();
        numberText.setBounds(10, 175, 200, 35);
        numberText.setBorder(null);
        numberText.setBackground(Color.white);
        form.add(numberText);

        JLabel contributions = new JLabel("Contibutions Amount");
        contributions.setFont(font);
        contributions.setBounds(10, 215, 250, 25);
        form.add(contributions);

        JTextField contributionText = new JTextField(20);
        contributionText.setBorder(null);
        contributionText.setBounds(10, 245, 200, 35);
        form.add(contributionText);

        // Done button sends the details to the database
        JButton DoneBtn = new JButton("Finish");
        DoneBtn.setBounds(10, 290, 165, 35);
        DoneBtn.setFont(font);
        DoneBtn.setFocusable(false);
        DoneBtn.setBorder(null);
        DoneBtn.setBackground(Color.red);
        form.add(DoneBtn);
        DoneBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String dbURL = "jdbc:mysql://localHost:3306/baraka_db";
                    Connection dbConnect = DriverManager.getConnection(dbURL, "root", "Mwise2002");

                    sqlst = dbConnect.prepareStatement(SQL_grp);
                    sqlst.setString(1, numberText.getText());
                    result = sqlst.executeQuery();// holds groupName from sql

                    if (result.next()) {
                        groupName = result.getString("group_name");
                        if (groupName != null) {
                            group = true;
                        }
                    }

                    String memb_sql = "insert into contributions (amount,member_id) values(?,?)"; // for the member
                                                                                                  // contribtuion
                    String grp_sql = "insert into contributions (amount,group_name) values(?,?)";// for the group
                                                                                                 // contribution

                    state = dbConnect.prepareStatement(memb_sql);
                    String member_id = numberText.getText();
                    int amount = Integer.parseInt(contributionText.getText());
                    if (amount < 1000) {
                        error.setText("Minimum amount is 1000");
                        return;
                    }
                    state.setString(2, member_id);
                    if (group) {
                        state.setInt(1, amount - 200);
                    } else {
                        state.setInt(1, amount);

                    }
                    state.setString(2, member_id);
                    state.executeUpdate();

                    statement = dbConnect.prepareStatement(grp_sql);
                    if (group) {
                        statement.setInt(1, 200);
                        statement.setString(2, groupName);
                    }
                    statement.executeUpdate();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Class Not Found, check the JAR");
                } catch (SQLException ex) {
                    Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("SQL is Bad!!!" + ex.getMessage());
                } catch (java.lang.NumberFormatException ex) {
                    error.setText("Enter a valid amount");
                    return;
                }

                back();
            }

        });

        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 325, 300, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(error);

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void back() {
        JFrame Home = new Home();
        Home.setVisible(true);
        this.dispose();
    }

}
