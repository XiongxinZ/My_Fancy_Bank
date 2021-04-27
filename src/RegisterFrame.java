import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame{
    public RegisterFrame() {
        super("Customer Registration");
    }

    private JPanel paintCustomerTextPanel(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel jp = new JPanel(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,10,20,20);
        JLabel info = new JLabel("<html><b>Customer Login</b>", JLabel.CENTER);
        layout.setConstraints(info, constraints);
        jp.add(info);

        constraints.insets = new Insets(10,10,10,20);
        JLabel usernameLabel = new JLabel("Username:");
//        label.setOpaque(true);
//        label.setBorder(BorderFactory.createBevelBorder(0));
//        label.setBackground(Color.LIGHT_GRAY);
        layout.setConstraints(usernameLabel, constraints);
        jp.add(usernameLabel);

        JTextField username = new JTextField();
        layout.setConstraints(username, constraints);
        jp.add(username);

        JLabel pwdLabel = new JLabel("Password:");
        layout.setConstraints(pwdLabel, constraints);
        jp.add(pwdLabel);

        JPasswordField password = new JPasswordField();
        layout.setConstraints(password, constraints);
        jp.add(password);

        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10,10,10,5);
        JButton loginButton = new JButton("Login");
        layout.setConstraints(loginButton,constraints);
        jp.add(loginButton);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,5,10,20);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setTextPanel(initPanel());
            }
        });
        layout.setConstraints(backButton,constraints);
        jp.add(backButton);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0,20,10,20);
        JLabel register = new JLabel("<html><u><em>Register</em></u>");
        register.setForeground(Color.BLUE);
        layout.setConstraints(register,constraints);
        jp.add(register);

        return jp;
    }
}
