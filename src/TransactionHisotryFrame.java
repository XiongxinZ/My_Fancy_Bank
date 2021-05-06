import javax.swing.*;
import java.awt.*;

public class TransactionHisotryFrame extends PopupFrame{


    public TransactionHisotryFrame(String c_id, String customerName) {
        super("Monetary Transaction Details");
        setFrame(c_id,customerName);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }

    private void setFrame(String customer_id,String customerName){
        setLayout(new GridLayout(0,1));
        add(new CustomerAccountPanel(customer_id, customerName));
        add(new TransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
        add(new StockTransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
    }
}
