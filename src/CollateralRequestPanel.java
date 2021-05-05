import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CollateralRequestPanel extends CustomerContentPanel{


    private DefaultTableModel dm;

    private JTable jt_customer;

    private List<CollateralValuation> valuationList = null;


    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        valuationList = CollateralDao.getInstance().selectCollateralRequestList(getCustomer().getId());


        for (CollateralValuation value : valuationList) {
//          "Request Date", "ID","Customer", "Name", "Status","Value","Solve Date"
            Vector<Object> v = new Vector<>();
            v.add(value.getRequestDate());
            v.add(value.getCvId());
            v.add(getCustomer().getName());
            v.add(value.getName());
            v.add(value.getStatus());
            v.add(value.getPrice()==null?"-":value.getPrice());
            v.add(value.getSolveDate());

            dm.addRow(v);
        }
    }
    public void fillTable(String year, String month, String day, String status) {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        valuationList = CollateralDao.getInstance().selectCollateralRequestList(getCustomer().getId());


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
            v.add(value.getPrice()==null?"-":value.getPrice());
            v.add(value.getSolveDate());

            dm.addRow(v);
        }
    }

    public CollateralRequestPanel(Customer customer) {
        super(customer);
        setPanel();
    }


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

        JLabel jl_status = new JLabel("Status");
        jp_tool.add(jl_status);

        JComboBox<String> jc_status = new JComboBox<>(new String[]{"All","Solving","Approve","Reject"});
        jp_tool.add(jc_status);

        jt_customer = new JTable(new DefaultTableModel(TableColumns.getCollateralRequestColumns(), 0) {
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

        JButton jb_refesh = new JButton("Refresh");
        jp_tool.add(jb_refesh);

        jb_refesh.addActionListener(new ActionListener() {

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
}
