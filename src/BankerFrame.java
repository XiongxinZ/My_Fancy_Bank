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
        contextPanel = new BankerHomepagePanel(banker);
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
        JMenuItem collateral_evals = new JMenuItem("Collateral Evaluation");
        JMenuItem daily_report = new JMenuItem("Daily Report");
        JMenuItem stock_evals = new JMenuItem("Stock Evaluation");
        JMenuItem item3 = new JMenuItem("Logout");
        JMenuItem item4 = new JMenuItem("Exit");
        homepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new BankerHomepagePanel(banker));
            }
        });
        customer_lists.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new CustomerListPanel(banker));
            }
        });

        collateral_evals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new CollateralEvalsPanel(banker));
            }
        });

        daily_report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new DailyReportPanel());
            }
        });

        stock_evals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new StockEvalsPanel());
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
        menu.add(collateral_evals);
        menu.add(daily_report);
        menu.add(stock_evals);
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
