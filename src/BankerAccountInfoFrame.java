public class BankerAccountInfoFrame extends PopupFrame{
    public BankerAccountInfoFrame(String c_id) {
        super("Customer Account Basic Details");
        setFrame(c_id);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        setVisible(true);
    }

    private void setFrame(String customer_id){
        CustomerHomepagePanel new_c = new CustomerHomepagePanel(CustomerDao.getInstance().selectCustomerWithCid(customer_id));
//        ((CustomerFrame)GuiUtil.getFrame(new_c)).setContextPanel(new MultiCurrAccountPanel(new_c.getCustomer().getAccount("Checking")));
//        ((CustomerFrame)GuiUtil.getFrame(new_c)).setContextPanel(new MultiCurrAccountPanel(new_c.getCustomer().getAccount("Saving")));
//        ((CustomerFrame)GuiUtil.getFrame(new_c)).setContextPanel(new MultiCurrAccountPanel(new_c.getCustomer().getAccount("Loan")));
//        ((CustomerFrame)GuiUtil.getFrame(new_c)).setContextPanel(new MultiCurrAccountPanel(new_c.getCustomer().getAccount("Security")));
        add(new_c);
    }
}
