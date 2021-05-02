import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class LoanPanel extends CustomerContentPanel implements MouseListener {

    private JTextField jtf_name;
    private JTextField jtf_id;
    private JTable jt_customer;
    private DefaultTableModel dm;
    private DefaultTableColumnModel dcm;

    private ValuePool<Loan> loanList = null;

    /**
     * 将客户信息填入表格
     *
     */
    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (loanList == null){
            loanList = LoanDao.selectCustomerLoanList(getCustomer());
        }

        for (Loan value : loanList.values()) {
//          "Loan ID", "Collateral ID","Balance", "Currency"
            Vector<Object> v = new Vector<>();
            v.add(value.getId());
            v.add(getCustomer().getName());
            v.add(value.getCollateral().getId());
            v.add(value.getBalance());
            v.add(value.getCurrency());

            dm.addRow(v);
        }
    }
    public void fillTable(String currency) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (loanList == null){
            loanList = LoanDao.selectCustomerLoanList(getCustomer());
        }

        for (Loan value : loanList.values()) {
            if (!value.getCurrency().equals(currency)){
                continue;
            }
            Vector<Object> v = new Vector<>();
            v.add(value.getId());
            v.add(getCustomer().getName());
            v.add(value.getCollateral().getId());
            v.add(value.getBalance());
            v.add(value.getCurrency());

            dm.addRow(v);
        }
    }

    public LoanPanel(Customer customer) {
        super(customer);
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

        JLabel currencyLabel = new JLabel("Currency");
        jp_tool.add(currencyLabel);

        JComboBox<String> currency = new JComboBox<>(new String[]{"USD","CNY","JPY"});
        jp_tool.add(currency);

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getLoanPoolColumns(), 0) {
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
        jp_tool.add(jb_submit);

        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String currType = ((String) currency.getSelectedItem()).trim();
                fillTable(currType);

            }
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int row = jt_customer.getSelectedRow();
//        if(e.getSource() == jl_del && row >= 0) {
//            Customer cs = new Customer();
//            cs.setId((String) jt_customer.getValueAt(row, 0));
//            Customer c = new CustomerDao().selectCustomerWithCid(cs.getId());
////            new CustomerInformationDialog(c, 1);
//        } else if(e.getSource() == jl_update && row >= 0) {
//            Customer cs = new Customer();
//            cs.setId((String) jt_customer.getValueAt(row, 0));
//            Customer c = new CustomerDao().selectCustomerWithCid(cs.getId());
////            new CustomerInformationDialog(c, 2);
//        } else if(e.getSource() == jl_refresh) {
//            fillTable();
//        }
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
//        if (e.getSource() == jl_add) {
//            jl_add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            jl_add.setText("<html><font color='#336699' style='font-weight:bold'>" + "添加" + "</font></html>");
//        } else if (e.getSource() == jl_del) {
//            jl_del.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            jl_del.setText("<html><font color='#336699' style='font-weight:bold'>" + "删除" + "</font></html>");
//        } else if (e.getSource() == jl_update) {
//            jl_update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            jl_update.setText("<html><font color='#336699' style='font-weight:bold'>" + "修改" + "</font></html>");
//        } else if (e.getSource() == jl_refresh) {
//            jl_refresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            jl_refresh.setText("<html><font color='#336699' style='font-weight:bold'>" + "刷新" + "</font></html>");
//        } else {
//
//        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        // TODO Auto-generated method stub
//        if (e.getSource() == jl_add) {
//            jl_add.setText("添加");
//        } else if (e.getSource() == jl_del) {
//            jl_del.setText("删除");
//        } else if (e.getSource() == jl_update) {
//            jl_update.setText("修改");
//        } else if (e.getSource() == jl_refresh) {
//            jl_refresh.setText("刷新");
//        } else {
//
//        }
    }


}
