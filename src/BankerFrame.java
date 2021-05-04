import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankerFrame extends CoreFrame {
    private Banker banker;
    private JPanel centerPanel;
    private JPanel contextPanel;
    private JPanel infoPanel;
    public BankerFrame(Banker banker){
        super("Manager System");
        this.banker = banker;
        centerPanel = new JPanel(new BorderLayout());
        setTopInfo();
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        contextPanel = new BankerHomepagePanel();
        centerPanel.add(contextPanel, BorderLayout.CENTER);
        add(centerPanel);

        setVisible(true);

    }

    private void setTopInfo(){
        infoPanel = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("<html>  Hello! <b><em>" + banker.getName() + "!</em></b>");
        infoPanel.add(setButton(), BorderLayout.EAST);
        infoPanel.add(jLabel, BorderLayout.CENTER);
    }

    private MyMenuButton setButton(){
        MyMenuButton button8 = new MyMenuButton("HomePage");
        JPopupMenu menu = new JPopupMenu("1");
        JMenuItem homepage = new JMenuItem("HomePage");
        JMenuItem customer_lists = new JMenuItem("Customer Lists");
        JMenuItem item2 = new JMenuItem("Loan Approval");
        JMenuItem item3 = new JMenuItem("Logout");
        JMenuItem item4 = new JMenuItem("Exit");
        homepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new BankerHomepagePanel());
            }
        });
        customer_lists.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new CustomerListPanel());
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setContextPanel(new StockTransactionHistoryPanel(banker));
            }
        });

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankerFrame.this.dispose();
                new LoginFrame();
            }
        });

        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });
        menu.add(homepage);
        menu.add(customer_lists);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        button8.addMenu(menu);
        return button8;
//        panel.add(button8);
//        panel.add(menu);

    }

    public void setContextPanel(JPanel jPanel){
        centerPanel.remove(contextPanel);
        contextPanel = jPanel;
        centerPanel.add(contextPanel, BorderLayout.CENTER);
        centerPanel.updateUI();
    }

    public JPanel getContextPanel() {
        return contextPanel;
    }
}
