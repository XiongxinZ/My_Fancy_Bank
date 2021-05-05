import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.List;

public class BuyStockFrame extends PopupFrame{
    private SecurityAccount account;
    private List<StockInfo> info;
    public BuyStockFrame(SecurityAccount account, List<StockInfo> info) {
        super("Buy Stock");
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

        JComboBox<StockInfo> box = new JComboBox<>(info.toArray(new StockInfo[0]));
        jPanel.add(box);

        JLabel amountLabel = new JLabel("Price: ");
        jPanel.add(amountLabel);

        double val = ((StockInfo)box.getSelectedItem()).getCurrentPrice();
        JLabel price = new JLabel(Double.toString(val));
        jPanel.add(price);

        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                double val = ((StockInfo)box.getSelectedItem()).getCurrentPrice();
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
                StockInfo stock = (StockInfo) box.getSelectedItem();
                double balance = account.getAmount(stock.getCurrency());
                double price = stock.getCurrentPrice();
                quantity.setText(String.valueOf((int) ((balance / price) * 0.1)));
            }
        });
        JButton pct20 = new JButton("20%");
        pct20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockInfo stock = (StockInfo) box.getSelectedItem();
                double balance = account.getAmount(stock.getCurrency());
                double price = stock.getCurrentPrice();
                quantity.setText(String.valueOf((int) ((balance / price) * 0.2)));
            }
        });
        JButton pct50 = new JButton("50%");
        pct50.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockInfo stock = (StockInfo) box.getSelectedItem();
                double balance = account.getAmount(stock.getCurrency());
                double price = stock.getCurrentPrice();
                quantity.setText(String.valueOf((int) ((balance / price) * 0.5)));
            }
        });
        JButton pct100 = new JButton("100%");
        pct100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockInfo stock = (StockInfo) box.getSelectedItem();
                double balance = account.getAmount(stock.getCurrency());
                double price = stock.getCurrentPrice();
                quantity.setText(String.valueOf((int) (balance / price) ));
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
                    StockInfo stockInfo = ((StockInfo) box.getSelectedItem());
                    String message = account.buyStock(stockInfo, amount);
                    BuyStockFrame.this.dispose();
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
                    new MessageFrame("Success", message);
                    //                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(DepositFrame.this)).getContextPanel()).repaintPanel();

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
                BuyStockFrame.this.dispose();
                new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }

}
