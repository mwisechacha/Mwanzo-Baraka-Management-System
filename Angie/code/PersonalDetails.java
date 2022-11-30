
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PersonalDetails extends MyFrame {
    JButton submit, addMember;
    JLabel Member, gender, error;
    JTextField NameText, IdText, Name2Text;
    JRadioButton Female, Male;
    JComboBox<String> year, date, month;
    Connection dbConnect;

    int counter = 1;
    int num;
    int strLen;
    PreparedStatement state;
    Boolean group;
    String name;

    String dates[] = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    String months[] = { "Jan", "feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sup", "Oct", "Nov", "Dec" };
    String years[] = { "1995", "1996", "1997", "1998",
            "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010",
            "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018",
            "2019", "2020", "2021", "2022" };

    public PersonalDetails(String nm, Boolean grp) {
        group = grp;
        name = nm;

        String dbURL = "jdbc:mysql://localHost:3306/baraka_db";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnect = DriverManager.getConnection(dbURL, "root",
                    "Mwise2002");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Font font = new Font("Times New Roman", Font.PLAIN, 15);
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

        // creating the labels,textfields for the titles,error message,name,id

        JLabel title0 = new JLabel("Hello, " + name);
        title0.setFont(new Font("Ink Free", Font.BOLD, 20));
        title0.setBounds(1, 20, 300, 30);
        form.add(title0);

        JLabel title1 = new JLabel("Thank you for registering :)");
        title1.setFont(new Font("Ink Free", Font.BOLD, 20));
        title1.setBounds(1, 50, 300, 30);
        form.add(title1);

        JLabel title2 = new JLabel("Please fill in the details below");
        title2.setFont(font);
        title2.setBounds(1, 100, 350, 20);
        form.add(title2);

        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.RED);
        error.setBounds(0, 520, 300, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(error);

        JLabel FirstName = new JLabel("First Name*");
        FirstName.setFont(font);
        FirstName.setBounds(10, 170, 165, 30);
        form.add(FirstName);

        NameText = new JTextField(20);
        NameText.setFont(font);
        NameText.setBounds(10, 200, 300, 30);
        NameText.setBorder(null);
        form.add(NameText);

        JLabel LastName = new JLabel("Last Name*");
        LastName.setFont(font);
        LastName.setBounds(10, 240, 165, 30);
        form.add(LastName);

        Name2Text = new JTextField(20);
        Name2Text.setFont(font);
        Name2Text.setBounds(10, 270, 300, 30);
        Name2Text.setBorder(null);
        form.add(Name2Text);

        JLabel id = new JLabel("ID Number*");
        id.setFont(font);
        id.setBounds(10, 310, 250, 30);
        form.add(id);

        IdText = new JTextField(20);
        IdText.setBounds(10, 340, 300, 30);
        IdText.setBorder(null);
        form.add(IdText);

        // using JRadioButton for selecting the gender
        gender = new JLabel("Gender");
        gender.setBounds(10, 370, 165, 30);
        gender.setFont(font);
        form.add(gender);

        Female = new JRadioButton("Female");
        Female.setBounds(10, 400, 120, 30);
        Female.setFocusable(false);
        Female.setHorizontalAlignment(SwingConstants.CENTER);
        Female.setBorder(null);
        Female.setBackground(Color.red);

        Male = new JRadioButton("Male");
        Male.setBounds(150, 400, 120, 30);
        Male.setFocusable(false);
        Male.setHorizontalAlignment(SwingConstants.CENTER);
        Male.setBorder(null);
        Male.setBackground(Color.red);

        ButtonGroup bg = new ButtonGroup();
        bg.add(Female);
        bg.add(Male);
        form.add(Female);
        form.add(Male);

        // using the comboBox for the selcting the date of birth
        JLabel Birth = new JLabel("Date of Birth");
        Birth.setBounds(10, 440, 165, 20);
        Birth.setFont(font);
        form.add(Birth);

        date = new JComboBox<String>(dates);
        date.setFont(font);
        date.setBounds(10, 465, 60, 20);
        date.setBorder(null);
        date.setFocusable(false);
        date.setBackground(Color.white);
        form.add(date);

        month = new JComboBox<String>(months);
        month.setFont(font);
        month.setBounds(70, 465, 60, 20);
        month.setBorder(null);
        month.setFocusable(false);
        month.setBackground(Color.white);
        form.add(month);

        year = new JComboBox<String>(years);
        year.setFont(font);
        year.setBounds(130, 465, 60, 20);
        year.setBorder(null);
        year.setFocusable(false);
        year.setBackground(Color.white);
        form.add(year);

        int submitWidth = 100;
        if (!group)
            submitWidth = 220;
        submit = new JButton("Submit");
        submit.setFocusable(false);
        submit.setBackground(Color.red);
        submit.setBorder(null);
        submit.setBounds(10, 490, submitWidth, 30);
        form.add(submit);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = fillData();
                if (response == 0)

                    submit();

            }

        });

        // addMember button that acts as a reset button to enter details of the second
        // member
        addMember = new JButton("ADD MEMBER");
        addMember.setFocusable(false);
        addMember.setBackground(Color.red);
        addMember.setBorder(null);
        addMember.setBounds(150, 490, 100, 30);
        if (group) {
            form.add(addMember);
        }
        addMember.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = fillData();

                if (response == 0) {
                    IdText.setText("");
                    NameText.setText("");
                    Name2Text.setText("");
                    bg.clearSelection();
                }

            }

        });

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void submit() {
        JFrame submit = new Home();
        submit.setVisible(true);
        this.dispose();

    }

    private int fillData() {
        try {
            // if the details aren't filled it diplays the error message
            if ((!(Female.isSelected() || Male.isSelected())) || IdText.getText() == "" || NameText.getText() == ""
                    || Name2Text.getText() == "") {
                error.setText("Please complete the form");
                return 1;
            }

            // inserting the information into mySQL in the members table
            PreparedStatement state;
            state = dbConnect.prepareStatement(
                    "insert into members (FName,LName,gender,DOB,id_number,group_name,reg_fee) values(?,?,?,?,?,?,?)");
            String FName = NameText.getText();
            String LName = Name2Text.getText();
            String id_number = IdText.getText();
            String DOB = year.getSelectedItem().toString();

            state.setString(1, FName);
            state.setString(2, LName);
            state.setString(5, id_number);
            state.setString(4, DOB);
            if (Female.isSelected()) {
                state.setString(3, "Female");

            } else {
                state.setString(3, "Male");
            }
            state.setString(5, id_number);
            if (group) {
                state.setString(6, name);
                state.setNull(7, 0);
            } else {
                state.setNull(6, 0);
                state.setInt(7, 2000);
            }

            state.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            error.setText("National ID is already registered");
            return 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;

    }
}
