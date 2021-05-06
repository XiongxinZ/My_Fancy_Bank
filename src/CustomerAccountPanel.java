import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class CustomerAccountPanel extends JPanel{

    private JTextField jtf_name;
    private JTextField jtf_id;
    private JTable jt_customer;
    private DefaultTableModel dm;
    private DefaultTableColumnModel dcm;

    private String c_id;
    private String customerName;


    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<String[]> list = AccountDao.getInstance().selectAccountList(c_id, customerName);

        for(String[] data : list) {
            dm.addRow(data);
        }
    }

//    public void fillTable(String type, String direction,String year, String month, String day,boolean hide) {
//        dm = (DefaultTableModel) jt_customer.getModel();
//        dm.setRowCount(0);
//
//        List<Vector<String>> list = TransactionDao.getInstance().getTransactionList(getCustomer(), type, direction,year, month, day,hide);
//
//        for(Vector<String> data : list) {
//            dm.addRow(data);
//        }
//    }

    public void fillTable(List<String[]> data) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        for(String[] v : data) {
            dm.addRow(v);
        }
    }

    public CustomerAccountPanel(String c_id,String customerName) {
        this.c_id = c_id;
        this.customerName = customerName;
        setPanel();
    }

    /**
     * Transaction History of the specific customer.
     */
//    public TransactionHistoryPanel() {
//        setPanel();
//    }

    private void setPanel(){
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel(new FlowLayout());
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        add(jp_tool, BorderLayout.NORTH);
//        jp_tool.setLayout(null);



        jt_customer = new JTable(new DefaultTableModel(TableColumns.getCustomerAccountColumns(), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        RowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jt_customer.getModel());
        jt_customer.setRowSorter(sorter);

        jt_customer.setRowHeight(30);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jt_customer.setDefaultRenderer(Object.class, r);
        TableSetting.makeFace(jt_customer);

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
        fillTable();
        JScrollPane js = new JScrollPane(jt_customer);
        this.add(js, BorderLayout.CENTER);

    }
}