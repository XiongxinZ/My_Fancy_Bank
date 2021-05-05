import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetStockPriceFrame extends PopupFrame{
    private Banker banker;
    private StockInfo stockInfo;
    public SetStockPriceFrame(String stockName, Banker banker) {
        super("Set Stock Price");
        this.banker = banker;
        this.stockInfo = StockDao.getInstance().selectStockInfo(stockName);
        setFrame();
        setVisible(true);
    }

    private void setFrame() {
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Stock: ");
        jPanel.add(typeLabel);

        JLabel name = new JLabel(stockInfo.getName());
        jPanel.add(name);

        JLabel amountLabel = new JLabel("Current Price: ");
        jPanel.add(amountLabel);

        JLabel amount = new JLabel(stockInfo.getCurrentPrice() + stockInfo.getCurrency());
        jPanel.add(amount);


        JLabel newPriceLabel = new JLabel("New Price: ");
        jPanel.add(newPriceLabel);

        JTextField price = new JTextField();
        jPanel.add(price);

        // TODO: add control for setting stock price
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double val = Double.parseDouble(price.getText());
                    stockInfo.setCurrentPrice(val);
                    StockDao.getInstance().updateStockInfo(stockInfo);
                    String massage = stockInfo.getName() + " price update! Current price is "+ stockInfo.getCurrentPrice() + stockInfo.getCurrency();
                    SetStockPriceFrame.this.dispose();
                    new BankerFrame(banker).setContextPanel(new StockEvalsPanel(banker));
                    new MessageFrame("Success", massage);

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
                new BankerFrame(banker).setContextPanel(new StockEvalsPanel(banker));

            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
