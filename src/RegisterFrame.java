import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame{
    public RegisterFrame() {
        super("Customer Registration");
        add(paintRegistrationTextPanel());
        setSize(700, 500);
        setVisible(true);
    }

    private JPanel paintRegistrationTextPanel(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel jp = new JPanel(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,10,20,20);
        JLabel info = new JLabel("<html><b>Customer Registration</b>", JLabel.CENTER);
        layout.setConstraints(info, constraints);
        jp.add(info);

        constraints.insets = new Insets(10,10,10,20);
        JLabel usernameLabel = new JLabel("Username:");
        layout.setConstraints(usernameLabel, constraints);
        jp.add(usernameLabel);

        JTextField username = new JTextField();
        layout.setConstraints(username, constraints);
        jp.add(username);

        JLabel pwdLabel = new JLabel("Password:");
        layout.setConstraints(pwdLabel, constraints);
        jp.add(pwdLabel);

        JTextField password = new JTextField();
        layout.setConstraints(password, constraints);
        jp.add(password);

        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10,10,10,5);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer newCustomer = new Customer(username.getText().trim(), password.getText().trim());
                CustomerDao.insertCustomer(newCustomer);
                new MessageFrame("IMPORTANT","<html>Your customer ID is <b><em>" + newCustomer.getId() + "</em></b>");
                dispose();
                new LoginFrame();
            }
        });
        layout.setConstraints(registerButton,constraints);
        jp.add(registerButton);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,5,10,20);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame("1");
            }
        });
        layout.setConstraints(backButton,constraints);
        jp.add(backButton);

        return jp;
    }
}
