import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class StockListPanel extends CustomerContentPanel {

    private DefaultTableModel dm;

    private List<StockInfo> stockList = null;
    private JTable jt_customer;


    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        stockList = StockDao.getInstance().selectStockInfoList();

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
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        stockList = StockDao.getInstance().selectStockInfoList();

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

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getStockListColumns(), 0) {
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
                    Double price = (Double) jt_customer.getModel().getValueAt(row,1);
                    String curr = (String) jt_customer.getModel().getValueAt(row,2);
                    StockInfo stockInfo = new StockInfo(sname,price,curr);
                    List<StockInfo> list =new ArrayList<>();
                    list.add(stockInfo);
                    GuiUtil.getFrame(StockListPanel.this).dispose();
                    new BuyStockFrame((SecurityAccount) getCustomer().getAccount("Security"), list);
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

        JButton jb_refesh = new JButton("Refresh");
        jp_tool.add(jb_refesh);

        jb_refesh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String currType = ((String) currency.getSelectedItem()).trim();
                fillTable(currType);

            }
        });


    }
}

