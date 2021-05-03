import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


public class StockListPanel extends TablePanel {

    private DefaultTableModel dm;

    private List<StockInfo> stockList = null;

    /**
     * 将客户信息填入表格
     *
     */
    public void fillTable() {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (stockList == null){
            stockList = StockDao.selectStockInfoList();
        }

        for (StockInfo value : stockList) {
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrentPrice());
            v.add(value.getCurrency());

            dm.addRow(v);
        }
    }
    public void fillTable(String currency) {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (stockList == null){
            stockList = StockDao.selectStockInfoList();
        }

        for (StockInfo value : stockList) {
            if (!value.getCurrency().equals(currency)){
                continue;
            }
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrentPrice());
            v.add(value.getCurrency());

            dm.addRow(v);
        }
    }

    public StockListPanel(Customer customer) {
        super(customer, TableColumns.getStockListColumns());
    }

}

