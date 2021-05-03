import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CollateralRequestPanel extends CustomerContentPanel implements MouseListener {


    private DefaultTableModel dm;

    private JTable jt_customer;

    private List<CollateralValuation> valuationList = null;

    /**
     * 将客户信息填入表格
     *
     */
    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (valuationList == null){
            valuationList = CollateralDao.selectCollateralRequestList(getCustomer().getId());
        }

        for (CollateralValuation value : valuationList) {
//          "Request Date", "ID","Customer", "Name", "Status","Value","Solve Date"
            Vector<Object> v = new Vector<>();
            v.add(value.getRequestDate());
            v.add(value.getCvId());
            v.add(getCustomer().getName());
            v.add(value.getName());
            v.add(value.getStatus());
            v.add(value.getPrice());
            v.add(value.getSolveDate());

            dm.addRow(v);
        }
    }
    public void fillTable(String year, String month, String day, String status) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (valuationList == null){
            valuationList = CollateralDao.selectCollateralRequestList(getCustomer().getId());
        }

        for (CollateralValuation value : valuationList) {
//          "Request Date", "ID","Customer", "Name", "Status","Value","Solve Date"
            Date date = value.getRequestDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int requestYear = calendar.get(Calendar.YEAR);
            int requestMonth = calendar.get(Calendar.MONTH);
            int requestDay = calendar.get(Calendar.DAY_OF_MONTH);

            if (!"All".equalsIgnoreCase(year) && requestYear != Integer.parseInt(year)){
                continue;
            }

            if (!"All".equalsIgnoreCase(month) && requestMonth != SystemDatabase.monthMap.get(month)){
                continue;
            }

            if (!"All".equalsIgnoreCase(day) && requestDay != Integer.parseInt(day)){
                continue;
            }

            if (!"All".equalsIgnoreCase(status) && !value.getStatus().equals(status)){
                continue;
            }

            Vector<Object> v = new Vector<>();
            v.add(date);
            v.add(value.getCvId());
            v.add(getCustomer().getName());
            v.add(value.getName());
            v.add(value.getStatus());
            v.add(value.getPrice());
            v.add(value.getSolveDate());

            dm.addRow(v);
        }
    }

    public CollateralRequestPanel(Customer customer) {
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

        JLabel currencyLabel = new JLabel("Request Date:");
        jp_tool.add(currencyLabel);


        JLabel jl_year = new JLabel("Year");
        jp_tool.add(jl_year);

        String[] year = new String[6];
        year[0] = "All";
        int j = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1; i < 6; i++) {
            year[i] = String.valueOf(j-i);
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

        JLabel jl_status = new JLabel("Status");
        jp_tool.add(jl_status);

        JComboBox<String> jc_status = new JComboBox<>(new String[]{"All","Solving","Approve","Reject"});
        jp_tool.add(jc_status);

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getCollateralRequestColumns(), 0) {
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

                String year = ((String)jc_year.getSelectedItem()).trim();
                String month = ((String)jc_month.getSelectedItem()).trim();
                String day = ((String)jc_day.getSelectedItem()).trim();
                String status = ((String)jc_status.getSelectedItem()).trim();
                fillTable(year, month, day, status);

            }
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int row = jt_customer.getSelectedRow();
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
