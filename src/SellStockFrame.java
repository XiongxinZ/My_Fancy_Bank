import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class SellStockFrame extends PopupFrame{
    private SecurityAccount account;
    private List<CustomerStock> info;
    public SellStockFrame(SecurityAccount account, List<CustomerStock> info) {
        super("Sell Stock");
        this.account = account;
        this.info = info;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){

        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Stock: ");
        jPanel.add(typeLabel);

        JComboBox<CustomerStock> box = new JComboBox<>(info.toArray(new CustomerStock[0]));
        jPanel.add(box);

        JLabel amountLabel = new JLabel("Price: ");
        jPanel.add(amountLabel);

        double val = ((CustomerStock)box.getSelectedItem()).getCurrentPrice();
        JLabel price = new JLabel(Double.toString(val));
        jPanel.add(price);

        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                double val = ((CustomerStock)box.getSelectedItem()).getCurrentPrice();
                price.setText(Double.toString(val));
            }
        });

        JLabel quantityLabel = new JLabel("Quantity: ");
        jPanel.add(quantityLabel);

        JTextField quantity = new JTextField();
        jPanel.add(quantity);

        JButton pct10 = new JButton("10%");
        pct10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerStock stock = (CustomerStock) box.getSelectedItem();
                quantity.setText(String.valueOf((int) (stock.getQuantity() * 0.1)));
            }
        });
        JButton pct20 = new JButton("20%");
        pct20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerStock stock = (CustomerStock) box.getSelectedItem();
                quantity.setText(String.valueOf((int)(stock.getQuantity() * 0.2)));
            }
        });
        JButton pct50 = new JButton("50%");
        pct50.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerStock stock = (CustomerStock) box.getSelectedItem();
                quantity.setText(String.valueOf((int)(stock.getQuantity() * 0.5)));
            }
        });
        JButton pct100 = new JButton("100%");
        pct100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerStock stock = (CustomerStock) box.getSelectedItem();
                quantity.setText(String.valueOf(stock.getQuantity()));
            }
        });
        jPanel.add(pct10);
        jPanel.add(pct20);
        jPanel.add(pct50);
        jPanel.add(pct100);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int amount = Integer.parseInt(quantity.getText().trim());
                    CustomerStock stock = ((CustomerStock) box.getSelectedItem());
                    String message = account.sellStock(stock, amount);
                    SellStockFrame.this.dispose();
                    new MessageFrame("Success", message);
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));

                }catch (NumberFormatException e1){
                    new MessageFrame("Input Error", "Please enter a integer");
                }
            }
        });
        jPanel.add(ok);

        JButton back = new JButton("Cancel");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }

}
