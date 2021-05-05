import javax.swing.*;
import java.awt.*;

public class DailyReportPanel extends JPanel {
    private Banker banker;
    public DailyReportPanel(Banker banker) {
//        setLayout(new BorderLayout());
        this.banker = banker;
        JLabel left = new JLabel(new ImageIcon("img/leftDaily.jpg"));

        JLabel right = new JLabel( new ImageIcon("img/rightDaily.jpg"));

        JPanel centerPanel = new JPanel(new GridLayout(0,2));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100,50,100,30));
//        setBorder(BorderFactory.createEmptyBorder(100,200,100,200));
        this.banker = banker;
        centerPanel.add(new JLabel("<html><font size = \"8\"><b>DAILY</b></font>"));
        centerPanel.add(new JLabel("<html><font size = \"8\"><b>REPORT</b></font>"));
        centerPanel.add(new JLabel("<html><font color = \"green\"><b>Transaction Type:</font>"));
        centerPanel.add(new JLabel("<html><font color = \"green\">Number of transactions</font>"));
        for (String type: SystemDatabase.transType) {
            centerPanel.add(new JLabel("<html><b>" + type + "</b>: "));
            centerPanel.add(new JLabel(String.valueOf(TransactionDao.getInstance().getNumberOfTransactions(type, "transactionLog", true))));
        }
        centerPanel.add(new JLabel("<html><font color = \"red\">Stock Transaction Type:</font>"));
        centerPanel.add(new JLabel("<html><font color = \"red\">Number of stock transactions</font>"));
        setLayout(new GridLayout(0,2));
        for (String type: SystemDatabase.stockTransType) {
            centerPanel.add(new JLabel("<html><b>" + type + "</b>: "));
            centerPanel.add(new JLabel(String.valueOf(TransactionDao.getInstance().getNumberOfTransactions(type, "stockTransactionLog", true))));
        }
        add(left,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
//        add(right,BorderLayout.EAST);
    }
}
