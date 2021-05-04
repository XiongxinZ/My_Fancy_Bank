import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

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


public class StockPositionPanel extends TablePanel {

    private DefaultTableModel dm;

    private ValuePool<CustomerStock> stockList = null;

    public void fillTable() {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (stockList == null){
            stockList = StockDao.getInstance().selectCustomerStockList(getCustomer());
        }

        for (CustomerStock value : stockList.values()) {
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrency());
            v.add(value.getCurrentPrice());
            v.add(value.getBuyPrice());
            v.add(value.getQuantity());
            v.add(value.getCurrentPrice()*value.getQuantity());
            v.add((value.getCurrentPrice()-value.getBuyPrice())*value.getQuantity());

            dm.addRow(v);
        }
    }
    public void fillTable(String currency) {
        dm = (DefaultTableModel) getJt_customer().getModel();
        dm.setRowCount(0);

        if (stockList == null){
            stockList = StockDao.getInstance().selectCustomerStockList(getCustomer());
        }

        for (CustomerStock value : stockList.values()) {
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            if (!value.getCurrency().equals(currency)){
                continue;
            }
            Vector<Object> v = new Vector<>();
            v.add(value.getName());
            v.add(value.getCurrency());
            v.add(value.getCurrentPrice());
            v.add(value.getBuyPrice());
            v.add(value.getQuantity());
            v.add(value.getCurrentPrice()*value.getQuantity());
            v.add((value.getCurrentPrice()-value.getBuyPrice())*value.getQuantity());

            dm.addRow(v);
        }
    }

    public StockPositionPanel(Customer customer) {
        super(customer, TableColumns.getStockPoolColumns());
    }

}

