
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.*;

public class Register extends MyFrame {

    JLabel name, fee, memberNo, OptionalMSG;
    int amount;
    int user = 0;
    PreparedStatement state;

    JTextField groupName, NumberText;
    JRadioButton individual, group;

    public Register() {
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
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

        JLabel registeration = new JLabel("REGISTRATION");
        registeration.setFont(font);
        // registeration.setFont(new Font("Ink Free",Font.BOLD,20));
        registeration.setBounds(10, 50, 300, 30);
        form.add(registeration);

        // selcting membership using the JRadioButton; individual or group
        JLabel membership = new JLabel("Select type of membership:");
        membership.setBounds(10, 100, 300, 20);
        membership.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        form.add(membership);

        individual = new JRadioButton("Individual");
        individual.setBounds(10, 150, 120, 30);
        individual.setFocusable(false);
        individual.setHorizontalAlignment(SwingConstants.CENTER);
        individual.setBorder(null);
        individual.setBackground(Color.red);
        individual.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (individual.isEnabled()) {
                    user = 0;
                    amount = 2000;
                    groupName.setEnabled(false);
                    fee.setText("Ksh " + amount);
                }

            }

        });

        group = new JRadioButton("Group");
        group.setBounds(150, 150, 120, 30);
        group.setFocusable(false);
        group.setHorizontalAlignment(SwingConstants.CENTER);
        group.setBorder(null);
        group.setBackground(Color.red);
        group.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (group.isEnabled()) {
                    amount = 5000;
                    fee.setText("Ksh " + amount);
                    groupName.setEnabled(true);
                }
            }

        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(individual);
        bg.add(group);
        form.add(individual);
        form.add(group);

        name = new JLabel("Group Name");
        name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        name.setBounds(10, 200, 180, 30);
        form.add(name);

        groupName = new JTextField();
        groupName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        groupName.setBorder(null);

        groupName.setBounds(10, 240, 250, 25);
        form.add(groupName);

        JLabel Rfee = new JLabel("Registration fee: ");
        Rfee.setBounds(10, 290, 250, 25);
        Rfee.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        form.add(Rfee);

        fee = new JLabel("Ksh " + amount);
        fee.setBounds(170, 290, 100, 25);
        fee.setFont(new Font("Times New Roman", Font.BOLD, 20));
        form.add(fee);

        JButton confirm = new JButton("Payment");
        confirm.setFocusable(false);
        confirm.setBackground(Color.red);
        confirm.setBorder(null);
        confirm.setBounds(60, 340, 100, 30);
        form.add(confirm);
        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    personalDetailsGroup();
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String dbURL = "jdbc:mysql://localHost:3306/baraka_db";
                        Connection dbConnect = DriverManager.getConnection(dbURL, "root", "Mwise2002");

                        String group_name = groupName.getText();
                        state = dbConnect
                                .prepareStatement("insert into group_details (group_name,reg_fee) values(?,?)");
                        state.setString(1, group_name);
                        state.setInt(2, 5000);
                        state.executeUpdate();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Class Not Found, check the JAR");
                    } catch (SQLException ex) {
                        Logger.getLogger(Mwanzo_Baraka.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("SQL is Bad!!!" + ex.getMessage());
                    }

                }

            }

        });

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void personalDetailsGroup() {
        JFrame personalDetailsGroup = new PersonalDetails(groupName.getText(), group.isSelected());// passing in the
                                                                                                   // group name and the
                                                                                                   // if group is
                                                                                                   // selected in
                                                                                                   // membership
                                                                                                   // radiobutton
        personalDetailsGroup.setVisible(true);
        this.dispose();
    }

}
