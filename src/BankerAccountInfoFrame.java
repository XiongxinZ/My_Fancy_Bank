public class BankerAccountInfoFrame extends PopupFrame{
    public BankerAccountInfoFrame(String c_id) {
        super("Customer Account Basic Details");
        setFrame(c_id);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }

    private void setFrame(String customer_id){
        add(new CustomerHomepagePanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id)));
    }
}
