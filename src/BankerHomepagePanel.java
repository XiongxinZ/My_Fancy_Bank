import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

public class BankerHomepagePanel extends JPanel implements MouseListener {

    private JTable jt_banker;
    private DefaultTableModel dm;
    private String dbName = "banker";

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void fillTable() {
        dm = (DefaultTableModel) jt_banker.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = null;
        if (dbName.equals("banker")) {
            list = BankerDao.getInstance().getBankers();
        } else if (dbName.equals("customer")) {
            list = BankerDao.getInstance().getCustomers();
        }

        for (Vector<String> data : list) {
            dm.addRow(data);
        }
    }

    public BankerHomepagePanel() {
        setPanel();
    }

    public BankerHomepagePanel(String dbName) {
        setDbName(dbName);
        setPanel();
    }

    private void setPanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel(new FlowLayout());
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        add(jp_tool, BorderLayout.NORTH);

        jt_banker = new JTable(new DefaultTableModel(TableColumns.getBankerColumns(), 0)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (dbName.equals("customer")) {
            jt_banker = new JTable(new DefaultTableModel(TableColumns.getCustomerColumns(), 0)) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }

        jt_banker.setRowHeight(30);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jt_banker.setDefaultRenderer(Object.class, r);
        TableSetting.makeFace(jt_banker);

        fillTable();
        JScrollPane js = new JScrollPane(jt_banker);
        this.add(js, BorderLayout.CENTER);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
