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

public class LoanPanel extends TablePanel {


    private DefaultTableModel dm;

    private ValuePool<Loan> loanList = null;

    /**
     * 将客户信息填入表格
     *
     */
    public void fillTable() {
        dm = (DefaultTableModel) getJt_customer().getModel();
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
        dm = (DefaultTableModel) getJt_customer().getModel();
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
        super(customer, TableColumns.getLoanPoolColumns());
    }

}
