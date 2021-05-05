import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
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


public class StockTransactionHistoryPanel extends CustomerContentPanel {

    private JTextField jtf_name;
    private JTextField jtf_id;
    private JTable jt_customer;
    private DefaultTableModel dm;
    private DefaultTableColumnModel dcm;




    public void fillTable(String type, String year, String month, String day) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = TransactionDao.getInstance().getStockTransactionList(getCustomer(), type,year, month, day);

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

    public StockTransactionHistoryPanel(Customer customer) {
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
//        jc_direction.setBounds(750, 10, 100, 30);
        jc_type.addItem("ALL");
        String[] directionList = {"Buy", "Sell"};
        for(String data : directionList) {
            jc_type.addItem(data);
        }
        jp_tool.add(jc_type);


        jt_customer = new JTable(new DefaultTableModel(TableColumns.getStockTransactionColumns(), 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jt_customer.setRowHeight(30);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jt_customer.setDefaultRenderer(Object.class, r);
        TableSetting.makeFace(jt_customer);

        String type = ((String) jc_type.getSelectedItem()).trim();
        String y = ((String)jc_year.getSelectedItem()).trim();
        String m = ((String)jc_month.getSelectedItem()).trim();
        String d = ((String)jc_day.getSelectedItem()).trim();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
        fillTable(type,y,m,d);
        JScrollPane js = new JScrollPane(jt_customer);
        this.add(js, BorderLayout.CENTER);

        JButton jb_submit = new JButton("Search");
//        jb_submit.setBounds(890, 10, 80, 30);
        jp_tool.add(jb_submit);

        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String type = ((String) jc_type.getSelectedItem()).trim();
                String year = ((String)jc_year.getSelectedItem()).trim();
                String month = ((String)jc_month.getSelectedItem()).trim();
                String day = ((String)jc_day.getSelectedItem()).trim();

//                StringBuilder query = QueryUtil.getAllQuery("transactionLog");
                fillTable(type,year,month,day);

            }
        });
    }
}