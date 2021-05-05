import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetStockPriceFrame extends PopupFrame{
    public SetStockPriceFrame(String stockName) {
        super("Set Stock Price");
        setFrame(stockName);
        setVisible(true);
    }

    private void setFrame(String stockName) {
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Stock: ");
        jPanel.add(typeLabel);

        JLabel name = new JLabel(stockName);
        jPanel.add(name);

        JLabel amountLabel = new JLabel("Price: ");
        jPanel.add(amountLabel);

        JTextField price = new JTextField();
        jPanel.add(price);

        // TODO: add control for setting stock price
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    int price = Integer.parseInt(price.getText().trim());

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

            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
