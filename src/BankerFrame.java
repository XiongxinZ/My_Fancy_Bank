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
        contextPanel = new DailyReportPanel(banker);
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
        JMenuItem homepage = new JMenuItem("Manager Lists");
        JMenuItem customer_lists = new JMenuItem("Customer Lists");
        JMenuItem collateral_evals = new JMenuItem("Collateral Evaluation");
        JMenuItem daily_report = new JMenuItem("HomePage");
        JMenuItem stock_evals = new JMenuItem("Stock Evaluation");
        JMenuItem item3 = new JMenuItem("Logout");
        JMenuItem item4 = new JMenuItem("Exit");
        homepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(homepage.getText());
                setContextPanel(new BankerHomepagePanel(banker));
                button8.setSelected(false);
            }
        });
        customer_lists.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(customer_lists.getText());
                setContextPanel(new CustomerListPanel(banker));
                button8.setSelected(false);
            }
        });

        collateral_evals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(collateral_evals.getText());
                setContextPanel(new CollateralEvalsPanel(banker));
                button8.setSelected(false);
            }
        });

        daily_report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(daily_report.getText());
                setContextPanel(new DailyReportPanel(banker));
                button8.setSelected(false);
            }
        });

        stock_evals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(stock_evals.getText());
                setContextPanel(new StockEvalsPanel(banker));
                button8.setSelected(false);
            }
        });

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(item3.getText());
                BankerFrame.this.dispose();
                new LoginFrame();
            }
        });

        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button8.setText(item4.getText());
                System.exit(-1);
            }
        });
        menu.add(daily_report);
        menu.add(homepage);
        menu.add(customer_lists);
        menu.add(collateral_evals);
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
