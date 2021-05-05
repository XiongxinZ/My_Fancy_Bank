public class StockTransactionHisotryFrame extends PopupFrame{

    public StockTransactionHisotryFrame(String c_id) {
        super("Stock Transaction Details");
        setFrame(c_id);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }

    private void setFrame(String customer_id){
        add(new StockTransactionHistoryPanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
    }
}