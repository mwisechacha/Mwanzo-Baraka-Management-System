
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends MyFrame {

    public Home() {

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

        JLabel title0 = new JLabel("Welcome to Mwanzo HomePage");
        title0.setFont(new Font("Ink Free", Font.BOLD, 20));
        title0.setBounds(20, 100, 350, 30);
        form.add(title0);

        // register button opens the personal details page
        JButton registerMember = new JButton("Register Member");
        registerMember.setBounds(50, 160, 220, 35);
        registerMember.setBackground(Color.RED);
        registerMember.setBorder(null);
        registerMember.setFocusable(false);
        registerMember.setFont(font);
        form.add(registerMember);
        registerMember.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                register();

            }

        });

        // opens the contributions the page
        JButton MonthlyCont = new JButton("Monthly Contributions");
        MonthlyCont.setBounds(50, 230, 220, 35);
        MonthlyCont.setBackground(Color.RED);
        MonthlyCont.setBorder(null);
        MonthlyCont.setFocusable(false);
        MonthlyCont.setFont(font);
        form.add(MonthlyCont);
        MonthlyCont.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contribute();

            }

        });

        // opens the loans page
        JButton Loans = new JButton("Loans");
        Loans.setBounds(50, 300, 220, 35);
        Loans.setBackground(Color.RED);
        Loans.setFocusable(false);
        Loans.setBorder(null);
        Loans.setFont(font);
        form.add(Loans);
        Loans.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loan();
            }

        });

        // opens the repayments page
        JButton Repayments = new JButton("Repayments");
        Repayments.setBounds(50, 370, 220, 35);
        Repayments.setBackground(Color.RED);
        Repayments.setFocusable(false);
        Repayments.setBorder(null);
        Repayments.setFont(font);
        form.add(Repayments);
        Repayments.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repay();
            }

        });

        // opens the reports page
        JButton Reports = new JButton("Reports");
        Reports.setBounds(50, 440, 220, 35);
        Reports.setFocusable(false);
        Reports.setBackground(Color.RED);
        Reports.setBorder(null);
        Reports.setFont(font);
        form.add(Reports);
        Reports.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                report();
            }

        });

        panel.add(logo);
        panel.add(form);
        this.add(panel);

    }

    private void register() {
        JFrame reg = new Register();
        reg.setVisible(true);
        this.dispose();
    }

    private void contribute() {
        JFrame cont = new Contributions();
        cont.setVisible(true);
        this.dispose();
    }

    private void loan() {
        JFrame Loans = new Loans();
        Loans.setVisible(true);
        this.dispose();
    }

    private void repay() {
        JFrame repayments = new repayments();
        repayments.setVisible(true);
        this.dispose();
    }

    private void report() {
        JFrame Reports = new Reports();
        Reports.setVisible(true);
        this.dispose();
    }

}
