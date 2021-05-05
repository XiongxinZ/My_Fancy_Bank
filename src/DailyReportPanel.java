import javax.swing.*;

public class DailyReportPanel extends JPanel {
    private Banker banker;
    public DailyReportPanel(Banker banker) {
        this.banker = banker;
        for (String type: SystemDatabase.transType) {
            this.add(new JLabel("Number of " + type + "'s: "));
            this.add(new JLabel(String.valueOf(TransactionDao.getInstance().getNumberOfTransactions(type, "transactionLog"))));
        }
        for (String type: SystemDatabase.stockTransType) {
            this.add(new JLabel("Number of " + type + "'s: "));
            this.add(new JLabel(String.valueOf(TransactionDao.getInstance().getNumberOfTransactions(type, "stockTransactionLog"))));
        }
    }
}
