import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class BankerContentPanel extends JPanel implements MouseListener {

    private JTable jt_banker;
    private DefaultTableModel dm;
    private String dbName = "banker";

    private JCheckBox jCheckBox = null;

    private Banker banker;

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
        } else if (dbName.equals("collateralValuation")) {
            list = CollateralDao.getInstance().getCollateralRequests();
        } else if (dbName.equals("stockInfo")) {
            list = StockDao.getInstance().getStocks();
        }

        for (Vector<String> data : list) {
            dm.addRow(data);
        }
    }

    public void fillTable(boolean selected) {
        dm = (DefaultTableModel) jt_banker.getModel();
        dm.setRowCount(0);

        List<Vector<String>> list = null;
        list = CollateralDao.getInstance().getCollateralRequests();

        for (Vector<String> data : list) {
            if (selected & !data.get(6).equalsIgnoreCase("Solving")) {
                continue;
            }
            dm.addRow(data);
        }
    }

    public BankerContentPanel(Banker banker) {
        this.banker = banker;
        setPanel();
    }

    public BankerContentPanel(String dbName, Banker banker) {
        this.banker = banker;
        setDbName(dbName);
        setPanel();
    }

    private void setPanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel jp_tool = new JPanel(new FlowLayout());
        jp_tool.setPreferredSize(new Dimension(1000, 50));

        if (dbName.equals("banker")) {
            JButton jButton = new JButton("Add");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new RegisterPopUpFrame();
                }
            });
            jp_tool.add(jButton);
        }


        add(jp_tool, BorderLayout.NORTH);

        jt_banker = new JTable(new DefaultTableModel(TableColumns.getBankerColumns(), 0)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        RowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jt_banker.getModel());
        jt_banker.setRowSorter(sorter);

        if (dbName.equals("customer")) {
            jt_banker = new JTable(new DefaultTableModel(TableColumns.getCustomerColumns(), 0)) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            jt_banker.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
                        // int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint());

                        String cellVal = (String) (jt_banker.getModel().getValueAt(row, 0));
                        new TransactionHisotryFrame(cellVal);
                        // new StockTransactionHisotryFrame(cellVal);
                        // new BankerAccountInfoFrame(cellVal);
                    }
                }
            });
        } else if (dbName.equals("collateralValuation")) {
            jCheckBox = new JCheckBox("only show solving", true);
            jCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fillTable(jCheckBox.isSelected());
                }
            });
            jp_tool.add(jCheckBox);

            jt_banker = new JTable(new DefaultTableModel(TableColumns.getCollateralRequestColumns_bankerVersion(), 0)) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            RowSorter<DefaultTableModel> sorter2 = new TableRowSorter<>((DefaultTableModel) jt_banker.getModel());
            jt_banker.setRowSorter(sorter2);

            jt_banker.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
                        // get cv_id
                        String cv_id = (String) (jt_banker.getModel().getValueAt(row, 2));
                        System.out.println(cv_id);
                        CollateralValuation collateralValuation = CollateralDao.getInstance().selectCollateralEvaluationWithRid(cv_id);
                        if (!collateralValuation.getStatus().equalsIgnoreCase("Solving")) {
                            new MessageFrame("Error!", "Sorry this request is solved, you can't modify");
                        } else {
                            GuiUtil.getFrame(BankerContentPanel.this).dispose();
                            new CollateralValuationFrame(cv_id, banker);
                        }
                    }
                }
            });
        } else if (dbName.equals("transactionLog")) {

        } else if (dbName.equals("stockInfo")) {
            JButton jButton = new JButton("Add");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(BankerContentPanel.this).dispose();
                    new AddStockFrame(banker);
                }
            });
            jp_tool.add(jButton);

            jt_banker = new JTable(new DefaultTableModel(TableColumns.getStockListColumns(), 0)) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            RowSorter<DefaultTableModel> sorter2 = new TableRowSorter<>((DefaultTableModel) jt_banker.getModel());
            jt_banker.setRowSorter(sorter2);

            jt_banker.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
                        // get stock name
                        String stockName = (String) (jt_banker.getModel().getValueAt(row, 0));
                        System.out.println(stockName);
                        GuiUtil.getFrame(BankerContentPanel.this).dispose();
                        new SetStockPriceFrame(stockName, banker);
                    }
                }
            });
        }

        jt_banker.setRowHeight(30);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jt_banker.setDefaultRenderer(Object.class, r);
        TableSetting.makeFace(jt_banker);

        if (jCheckBox != null) {
            fillTable(jCheckBox.isSelected());
        } else {
            fillTable();
        }
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

    public Banker getBanker() {
        return banker;
    }
}
