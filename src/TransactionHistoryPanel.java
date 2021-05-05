import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class TransactionHistoryPanel extends CustomerContentPanel{

    private JTextField jtf_name;
    private JTextField jtf_id;
    private JTable jt_customer;
    private DefaultTableModel dm;
    private DefaultTableColumnModel dcm;


    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = TransactionDao.getInstance().getTransactionList(getCustomer());

        for(Vector<String> data : list) {
            dm.addRow(data);
        }
    }

    public void fillTable(String type, String direction,String year, String month, String day,boolean hide) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = TransactionDao.getInstance().getTransactionList(getCustomer(), type, direction,year, month, day,hide);

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

        JLabel jl_year = new JLabel("Year");
        jp_tool.add(jl_year);

        String[] year = new String[6];
        year[0] = "All";
        int j = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1; i < 6; i++) {
            year[i] = String.valueOf(j-i+1);
        }
        JComboBox<String> jc_year = new JComboBox<>(year);
        jp_tool.add(jc_year);

        JLabel jl_month= new JLabel("Month");
        jp_tool.add(jl_month);

        JComboBox<String> jc_month = new JComboBox<>(SystemDatabase.month);
        jp_tool.add(jc_month);

        JLabel jl_day = new JLabel("Day");
        jp_tool.add(jl_day);

        JComboBox<String> jc_day = new JComboBox<>(SystemDatabase.day);
        jp_tool.add(jc_day);

        JLabel jl_type = new JLabel("Type");
        jp_tool.add(jl_type);

        JComboBox<String> jc_type = new JComboBox<>();
//        jc_type.setBounds(750, 10, 100, 30);
        jc_type.addItem("ALL");
        String[] typeList = SystemDatabase.transType;
        for(String data : typeList) {
            jc_type.addItem(data);
        }
        jp_tool.add(jc_type);

        JLabel jl_to = new JLabel("Direction");
//        jl_to.setBounds(700, 10, 50, 30);
        jp_tool.add(jl_to);

        JComboBox<String> jc_direction = new JComboBox<>();
//        jc_direction.setBounds(750, 10, 100, 30);
        jc_direction.addItem("ALL");
        String[] directionList = {"IN", "OUT"};
        for(String data : directionList) {
            jc_direction.addItem(data);
        }
        jp_tool.add(jc_direction);

//        JLabel jl_hide = new JLabel("Interest");
////        jl_to.setBounds(700, 10, 50, 30);
//        jp_tool.add(jl_hide);
//
//        JComboBox<String> jc_hide = new JComboBox<>(new String[]{"hide","show"});
//        jp_tool.add(jc_hide);

        JCheckBox jCheckBox = new JCheckBox("hide interest", true);
        jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = ((String) jc_type.getSelectedItem()).trim();
                String direction = ((String) jc_direction.getSelectedItem()).trim();
                String y = ((String)jc_year.getSelectedItem()).trim();
                String m = ((String)jc_month.getSelectedItem()).trim();
                String d = ((String)jc_day.getSelectedItem()).trim();
                boolean hide = jCheckBox.isSelected();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
                fillTable(type, direction,y,m,d,hide);
            }
        });
        jp_tool.add(jCheckBox);


        jt_customer = new JTable(new DefaultTableModel(TableColumns.getTransactionColumns(), 0) {
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

        String type = ((String) jc_type.getSelectedItem()).trim();
        String direction = ((String) jc_direction.getSelectedItem()).trim();
        String y = ((String)jc_year.getSelectedItem()).trim();
        String m = ((String)jc_month.getSelectedItem()).trim();
        String d = ((String)jc_day.getSelectedItem()).trim();
        boolean hide = jCheckBox.isSelected();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
        fillTable(type, direction,y,m,d,hide);
        JScrollPane js = new JScrollPane(jt_customer);
        this.add(js, BorderLayout.CENTER);

        JButton jb_submit = new JButton("Search");
//        jb_submit.setBounds(890, 10, 80, 30);
        jp_tool.add(jb_submit);

        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String type = ((String) jc_type.getSelectedItem()).trim();
                String direction = ((String) jc_direction.getSelectedItem()).trim();
                String year = ((String)jc_year.getSelectedItem()).trim();
                String month = ((String)jc_month.getSelectedItem()).trim();
                String day = ((String)jc_day.getSelectedItem()).trim();
                boolean hide = jCheckBox.isSelected();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
                fillTable(type, direction,year,month,day,hide);

            }
        });
    }
}

