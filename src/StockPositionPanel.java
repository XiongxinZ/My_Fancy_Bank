import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class StockPositionPanel extends CustomerContentPanel {

    private DefaultTableModel dm;
    private JTable jt_customer;
    private ValuePool<CustomerStock> stockList = null;

    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
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
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (stockList == null){
            stockList = StockDao.getInstance().selectCustomerStockList(getCustomer());
        }

        for (CustomerStock value : stockList.values()) {
//             "Stock Name", "Current Price", "Buy Price", "Quantity", "Amount", "Profit"
            if (!currency.equalsIgnoreCase("All")&&!value.getCurrency().equals(currency)){
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
        super(customer);
        setPanel();
    }


    private void setPanel(){
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel(new FlowLayout());
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        add(jp_tool, BorderLayout.NORTH);
//        jp_tool.setLayout(null);

        JLabel currencyLabel = new JLabel("Currency");
        jp_tool.add(currencyLabel);

        JComboBox<String> currency = new JComboBox<>(new String[]{"All","USD","CNY","JPY"});
        jp_tool.add(currency);

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getStockPoolColumns(), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        RowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jt_customer.getModel());
        jt_customer.setRowSorter(sorter);

        jt_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row =((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint());
                    assert jt_customer.getModel().getColumnCount() == 3;
                    String sname = (String) jt_customer.getModel().getValueAt(row,0);
                    CustomerStock stock = ((SecurityAccount)getCustomer().getAccount("Security")).getStockPool().get(sname);
                    List<CustomerStock> list =new ArrayList<>();
                    list.add(stock);
                    GuiUtil.getFrame(StockPositionPanel.this).dispose();
                    new SellStockFrame((SecurityAccount) getCustomer().getAccount("Security"), list);
                }
            }
        });

        jt_customer.setRowHeight(30);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jt_customer.setDefaultRenderer(Object.class, r);
        TableSetting.makeFace(jt_customer);

        fillTable();
        JScrollPane js = new JScrollPane(jt_customer);
        this.add(js, BorderLayout.CENTER);

        JButton jb_submit = new JButton("Search");
        jp_tool.add(jb_submit);

        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String currType = ((String) currency.getSelectedItem()).trim();
                fillTable(currType);

            }
        });

    }


}

