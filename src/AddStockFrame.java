import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddStockFrame extends PopupFrame{
    private Banker banker;
    public AddStockFrame(Banker banker) {
        super("Add Stock");
        this.banker = banker;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(4,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel balanceLabel = new JLabel("Stock Name: ");
        jPanel.add(balanceLabel);

        JTextField name = new JTextField();
        jPanel.add(name);

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);


        JLabel amountLabel = new JLabel("Price: ");
        jPanel.add(amountLabel);

        JTextField price = new JTextField();
        jPanel.add(price);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(price.getText().trim());
                    String stockName = name.getText();
                    String cur = ((String) box.getSelectedItem()).trim();
                    StockInfo stockInfo = new StockInfo(stockName, amount, cur);

                    int flag = StockDao.getInstance().insertStockInfo(stockInfo);
                    if (flag == 1){
                        new BankerFrame(banker).setContextPanel(new StockEvalsPanel(banker));
//                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(WithdrawFrame.this)).getContextPanel()).repaintPanel();
                        String message = "insert Stock! Name: "+ stockName + " Price: "+ amount + " "+cur;
                        AddStockFrame.this.dispose();
                        new MessageFrame("Add Success", message);
                    }else{
                        new MessageFrame("Error", "Stock exist!");
                    }
                }catch (NumberFormatException e1){
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        jPanel.add(ok);

        JButton back = new JButton("Cancel");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStockFrame.this.dispose();
                new BankerFrame(banker).setContextPanel(new StockEvalsPanel(banker));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
