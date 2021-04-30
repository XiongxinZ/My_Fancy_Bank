import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerFrame extends CoreFrame {
    private Customer customer;
    private JPanel centerPanel;
    private JPanel contextPanel;
    private JPanel infoPanel;
    public CustomerFrame(Customer customer){
        super("Customer System");
        this.customer = customer;
        centerPanel = new JPanel(new BorderLayout());
        setTopInfo();
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        contextPanel = new CustomerHomepagePanel(customer);
        centerPanel.add(contextPanel, BorderLayout.CENTER);
        add(centerPanel);

        setVisible(true);

    }

    private void setTopInfo(){
        infoPanel = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("<html>Hello! <b><em>" + customer.getName() + "!</em></b>");
        infoPanel.add(setButton(), BorderLayout.EAST);
        infoPanel.add(jLabel, BorderLayout.CENTER);
    }

    private MyMenuButton setButton(){
        MyMenuButton button8 = new MyMenuButton("HomePage");
        JPopupMenu menu = new JPopupMenu("1");
        JMenuItem homepage = new JMenuItem("HomePage");
        JMenuItem transHis = new JMenuItem("Transaction History");
        JMenuItem item2 = new JMenuItem("12");
        JMenuItem item3 = new JMenuItem("13");
        JMenuItem item4 = new JMenuItem("14");
        homepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new CustomerHomepagePanel(customer));
            }
        });
        transHis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContextPanel(new TransactionHistoryPanel(customer));
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                label.setText("pick 12");
//                button8.setText("¡ø"+ item2.getText());
//                button8.setSelected(false);
            }
        });

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                label.setText("pick 13");
//                button8.setText("¡ø"+ item3.getText());
//                button8.setSelected(false);
            }
        });

        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                label.setText("pick 14");
//                button8.setText("¡ø"+ item4.getText());
//                button8.setSelected(false);
            }
        });
        menu.add(homepage);
        menu.add(transHis);
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
