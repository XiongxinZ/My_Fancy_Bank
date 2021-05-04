import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

public class CollateralPanel extends CustomerContentPanel{


    private DefaultTableModel dm;
    private JTable jt_customer;

    private List<Collateral> collList = null;

    public void fillTable() {
        dm = (DefaultTableModel) jt_customer.getModel();
        dm.setRowCount(0);

        if (collList == null){
            collList = CollateralDao.getInstance().selectCollateralList(getCustomer());
        }

        for (Collateral value : collList) {
            // "Collateral ID", "Customer","Value", "Currency","used"
            Vector<Object> v = new Vector<>();
            v.add(value.getId());
            v.add(getCustomer().getName());
            v.add(value.getPrice());
            v.add("USD");
            v.add(value.isUsed()?1:0);

            dm.addRow(v);
        }
    }

    public CollateralPanel(Customer customer) {
        super(customer);
        setPanel();
    }


    private void setPanel(){
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel(new FlowLayout());
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        add(jp_tool, BorderLayout.NORTH);
//        jp_tool.setLayout(null);


        jt_customer = new JTable(new DefaultTableModel(TableColumns.getCollateralColumns(), 0) {
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
    }

}
