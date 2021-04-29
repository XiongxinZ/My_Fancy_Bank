import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


public class TransactionHistoryPanel extends CustomerContentPanel implements MouseListener {

    private JTextField jtf_name;
    private JTextField jtf_id;
    private JTable jt_customer;
    private DefaultTableModel dm;
    private DefaultTableColumnModel dcm;
    private JLabel jl_add;
    private JLabel jl_del;
    private JLabel jl_update;
    private JLabel jl_refresh;

    private Customer customer;

    /**
     * 将客户信息填入表格
     *
     */
    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = TransactionDao.getTransactionList(customer);

        for(Vector<String> data : list) {
            dm.addRow(data);
        }
    }

    public void fillTable(String type) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = TransactionDao.getTransactionList(customer, type);

        for(Vector<String> data : list) {
            dm.addRow(data);
        }
    }

    public void fillTable(List<String[]> data) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        for(String[] v : data) {
            dm.addRow(v);
        }
    }

    public TransactionHistoryPanel(Customer customer) {
        super(customer);
    }

    /**
     * Transaction History of the specific customer.
     */
//    public TransactionHistoryPanel() {
//        setPanel();
//    }

    private void setPanel(){
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel();
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        add(jp_tool, BorderLayout.NORTH);
        jp_tool.setLayout(null);

        JLabel jl_type = new JLabel("Transaction Type");
        jl_type.setBounds(352, 10, 50, 30);
        jp_tool.add(jl_type);

        JComboBox<String> jc_type = new JComboBox<>();
        jc_type.setBounds(750, 10, 100, 30);
        jp_tool.add(jc_type);
        jc_type.addItem("All");
        String[] typeList = ComboList.getTransactionTypeList();
        for(String data : typeList) {
            jc_type.addItem(data);
        }

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getTransactionColumns(), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
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
        jb_submit.setBounds(890, 10, 80, 30);
        jp_tool.add(jb_submit);

        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String type = ((String) jc_type.getSelectedItem()).trim();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
                if(!("All").equals(type)){
                    fillTable(type);
                }else{
                    fillTable();
                }
            }
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int row = jt_customer.getSelectedRow();
        if(e.getSource() == jl_add) {
            Customer cs = new Customer();
//            new CustomerInformationDialog(cs, 0);
        } else if(e.getSource() == jl_del && row >= 0) {
            Customer cs = new Customer();
            cs.setId((String) jt_customer.getValueAt(row, 0));
            Customer c = new CustomerDao().selectCustomerWithCid(cs.getId());
//            new CustomerInformationDialog(c, 1);
        } else if(e.getSource() == jl_update && row >= 0) {
            Customer cs = new Customer();
            cs.setId((String) jt_customer.getValueAt(row, 0));
            Customer c = new CustomerDao().selectCustomerWithCid(cs.getId());
//            new CustomerInformationDialog(c, 2);
        } else if(e.getSource() == jl_refresh) {
            fillTable();
        } else {

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == jl_add) {
            jl_add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jl_add.setText("<html><font color='#336699' style='font-weight:bold'>" + "添加" + "</font></html>");
        } else if (e.getSource() == jl_del) {
            jl_del.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jl_del.setText("<html><font color='#336699' style='font-weight:bold'>" + "删除" + "</font></html>");
        } else if (e.getSource() == jl_update) {
            jl_update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jl_update.setText("<html><font color='#336699' style='font-weight:bold'>" + "修改" + "</font></html>");
        } else if (e.getSource() == jl_refresh) {
            jl_refresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jl_refresh.setText("<html><font color='#336699' style='font-weight:bold'>" + "刷新" + "</font></html>");
        } else {

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == jl_add) {
            jl_add.setText("添加");
        } else if (e.getSource() == jl_del) {
            jl_del.setText("删除");
        } else if (e.getSource() == jl_update) {
            jl_update.setText("修改");
        } else if (e.getSource() == jl_refresh) {
            jl_refresh.setText("刷新");
        } else {

        }
    }

}

