public class TransactionHisotryFrame extends PopupFrame{

    public TransactionHisotryFrame(String c_id) {
        super("Transaction Details");
        setFrame(c_id);
        setVisible(true);
    }

    private void setFrame(String customer_id){
        add(new TransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
    }
}
