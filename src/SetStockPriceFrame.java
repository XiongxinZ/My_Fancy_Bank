import javax.swing.*;
import java.awt.*;

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


    }
}
