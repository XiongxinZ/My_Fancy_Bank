import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginFrame extends CoreFrame {

    GridBagLayout layout;
//    GridBagConstraints constraints = new GridBagConstraints();
    JPanel centerPanel;
    JLabel imgLabel = new JLabel();;
    JPanel textPanel = paintCustomerTextPanel();
    ImageIcon img;

    public LoginFrame(String type) {
        this();
        setTextPanel(paintCustomerTextPanel());
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }



    public LoginFrame() {
        super("Login");
//        layout = new GridBagLayout();
        centerPanel = new JPanel(new BorderLayout());
        buildPanel();
        add(centerPanel);

        setVisible(true);
    }

    public void buildPanel(){
//        constraints.fill = GridBagConstraints.BOTH;
//        constraints.weightx = 1.0;
//        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JLabel welcomeLabel = new JLabel("<html><b><font color = \"#FF0000\">Welcome To Bank Management System!</font></b>",JLabel.CENTER);
//        layout.setConstraints(welcomeLabel,constraints);
        welcomeLabel.setPreferredSize(new Dimension(600, 50));
        centerPanel.add(welcomeLabel, BorderLayout.NORTH);

//        imgLabel.setPreferredSize(new Dimension(400,400));

        imgLabel = new JLabel("Login img"){
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("img/login.png");
                g.drawImage(icon.getImage(), 0, -(icon.getIconHeight()*getWidth()/icon.getIconWidth()-getHeight())/3, getWidth(),icon.getIconHeight()*getWidth()/icon.getIconWidth(),
                        icon.getImageObserver());
            }
        };
        centerPanel.add(imgLabel, BorderLayout.CENTER);


        textPanel = initPanel();
        centerPanel.add(textPanel, BorderLayout.EAST);
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
        JLabel usernameLabel = new JLabel("Email:");
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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer customer = CustomerDao.getInstance().selectCustomer(username.getText(), String.valueOf(password.getPassword()));
                if (customer == null){
                    new MessageFrame("Login Error", "No user or password error");
                }else{
                    LoginFrame.this.dispose();
                    new CustomerFrame(customer);
                }
            }
        });
        layout.setConstraints(loginButton,constraints);
        jp.add(loginButton);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,5,10,20);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextPanel(initPanel());
            }
        });
        layout.setConstraints(backButton,constraints);
        jp.add(backButton);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0,20,10,20);
        JLabel register = new JLabel("<html><u><em>Register</em></u>");
        register.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegisterFrame();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                register.setText("<html><b><em>Register</em></b>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                register.setText("Register");
            }
        });
        register.setForeground(Color.BLUE);
        layout.setConstraints(register,constraints);
        jp.add(register);

        return jp;
    }

    private JPanel paintBankerTextPanel(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel jp = new JPanel(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,10,20,20);
        JLabel info = new JLabel("<html><b>Manager Login</b>", JLabel.CENTER);
        layout.setConstraints(info, constraints);
        jp.add(info);

        constraints.insets = new Insets(10,10,10,20);
        JLabel usernameLabel = new JLabel("Email:");
//        label.setOpaque(true);
//        label.setBorder(BorderFactory.createBevelBorder(0));
//        label.setBackground(Color.LIGHT_GRAY);
        layout.setConstraints(usernameLabel, constraints);
        jp.add(usernameLabel);

        JTextField username = new JTextField("admin@bu.edu");
        layout.setConstraints(username, constraints);
        jp.add(username);

        JLabel pwdLabel = new JLabel("Password:");
        layout.setConstraints(pwdLabel, constraints);
        jp.add(pwdLabel);

        JPasswordField password = new JPasswordField("admin");
        layout.setConstraints(password, constraints);
        jp.add(password);

        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10,10,10,5);
        JButton loginButton = new JButton("Login");
        // Banker login control
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Banker banker = BankerDao.getInstance().selectBanker(username.getText(), String.valueOf(password.getPassword()));
                if (banker == null){
                    new MessageFrame("Login Error", "No user or password error");
                }else{
                    LoginFrame.this.dispose();
                    new BankerFrame(banker);
                }
            }
        });
        layout.setConstraints(loginButton,constraints);
        jp.add(loginButton);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,5,10,20);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextPanel(initPanel());
            }
        });
        layout.setConstraints(backButton,constraints);
        jp.add(backButton);

        constraints.insets = new Insets(0,20,10,20);
        JLabel register = new JLabel("<html><em>Manager user can only<br> be created by existing <br>manager.</em>");
        register.setForeground(Color.GRAY);
        layout.setConstraints(register,constraints);
        jp.add(register);

        return jp;
    }


    private JPanel initPanel(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel jp = new JPanel(layout);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10,10,20,20);
        JLabel info = new JLabel("<html><b>Who you are?</b>", JLabel.CENTER);
        layout.setConstraints(info, constraints);
        jp.add(info);

        constraints.insets = new Insets(30,30,30,40);
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JButton customer = new JButton("Customer");
        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextPanel(paintCustomerTextPanel());
            }
        });
        layout.setConstraints(customer,constraints);
        jp.add(customer);

        JButton manager = new JButton("Manager");
        manager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextPanel(paintBankerTextPanel());
            }
        });
        layout.setConstraints(manager,constraints);
        jp.add(manager);

        return jp;
    }

    private void setTextPanel(JPanel jPanel){
        centerPanel.remove(textPanel);
        textPanel = jPanel;
        centerPanel.add(textPanel, BorderLayout.EAST);
        centerPanel.updateUI();
    }

}
