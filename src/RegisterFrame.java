import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends CoreFrame{
    private JLabel leftBar;
    private JLabel rightBar;

    private JPanel centerPanel;


    public RegisterFrame() {
        super("Customer Registration");
        buildPanel();
        add(centerPanel);

        setVisible(true);
    }

    private void buildPanel(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        centerPanel = new JPanel(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,0,0,40);

        leftBar = new JLabel("leftBar"){
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("img/left.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(),getHeight(),
                        icon.getImageObserver());
            }
        };

        layout.setConstraints(leftBar, constraints);
        centerPanel.add(leftBar);
//        leftBar.setPreferredSize(new Dimension((int) (getWidth()*0.2),getHeight()));


        constraints.insets = new Insets(10,20,10,20);
        JPanel textPanel = paintRegistrationTextPanel();
        layout.setConstraints(textPanel,constraints);
        centerPanel.add(textPanel);


        constraints.gridwidth = GridBagConstraints.REMAINDER;

        constraints.insets = new Insets(0,40,0,0);
        rightBar = new JLabel("leftBar"){
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("img/right.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(),getHeight(),
                        icon.getImageObserver());
            }
        };
        layout.setConstraints(rightBar, constraints);
        centerPanel.add(rightBar);

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

        constraints.insets = new Insets(10,10,10,20);
        JLabel emailLabel = new JLabel("Email:");
        layout.setConstraints(emailLabel, constraints);
        jp.add(emailLabel);

        JTextField email = new JTextField();
        layout.setConstraints(email, constraints);
        jp.add(email);

        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10,10,10,5);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer newCustomer = new Customer(username.getText().trim(), password.getText().trim(), email.getText());
                int flag = CustomerDao.getInstance().insertCustomer(newCustomer);
                if (flag == 1){
                    dispose();
                    new LoginFrame();
                    new MessageFrame("IMPORTANT","<html>Your customer ID is <b><em>" + newCustomer.getId() + "</em></b>");
                }else{
                    new MessageFrame("Error", "Email exists.");
                }
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
//
//    private JLabel setSideImg(String filename){
//        JLabel label = new JLabel(new ImageIcon(filename));
//        return label;
//    }
}
