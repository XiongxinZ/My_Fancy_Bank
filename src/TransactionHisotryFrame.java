import javax.swing.*;
import java.awt.*;

public class TransactionHisotryFrame extends PopupFrame{

    public TransactionHisotryFrame(String c_id) {
        super("Transaction Details");
        setFrame(c_id);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }

    private void setFrame(String customer_id){
        setLayout(new GridLayout(0,1));
        add(new TransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
        add(new StockTransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
    }
}
