import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class StockProfitPanel extends TablePanel {

    private DefaultTableModel dm;

    private ValuePool<StockProfit> profitList = null;


    public void fillTable() {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (profitList == null){
            profitList = StockDao.getInstance().selectProfitList(getCustomer());
        }

        for (StockProfit value : profitList.values()) {
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrency());
            v.add(value.getProfit());
            dm.addRow(v);
        }
    }
    public void fillTable(String currency) {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (profitList == null){
            profitList = StockDao.getInstance().selectProfitList(getCustomer());
        }

        for (StockProfit value : profitList.values()) {
            if (!value.getCurrency().equals(currency)){
                continue;
            }
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrency());
            v.add(value.getProfit());
            dm.addRow(v);
        }
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);
    }

    public StockProfitPanel(Customer customer) {
        super(customer, TableColumns.getStockProfitColumns());
    }
}
