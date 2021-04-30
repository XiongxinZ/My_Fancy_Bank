import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerHomepagePanel extends CustomerContentPanel{
    public CustomerHomepagePanel(Customer customer) {
        super(customer);
        setPanel();
    }


    private void setPanel(){
        GridLayout gridLayout = new GridLayout(2,2, 30, 30);
        setLayout(gridLayout);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton checking = new JButton(StringUtil.getHomepageButtonString(getCustomer(),"Checking"));
        checking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCustomer().getAccount("Checking") == null){
//                    new MessageFrame("Create Account",CheckingAccount.createAccount(getCustomer()));
                    GuiUtil.getFrame(CustomerHomepagePanel.this).dispose();
                    new CreateAccountFrame(getCustomer(), "Checking");
                }else{
                    ((CustomerFrame)GuiUtil.getFrame(CustomerHomepagePanel.this)).setContextPanel(new MultiCurrAccountPanel(getCustomer().getAccount("Checking")));
                }
            }
        });
        add(checking);

        JButton saving = new JButton(StringUtil.getHomepageButtonString(getCustomer(),"Saving"));
        saving.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCustomer().getAccount("Saving") == null){
//                    new MessageFrame("Create Account",SavingAccount.createAccount(getCustomer()));

                    GuiUtil.getFrame(CustomerHomepagePanel.this).dispose();
                    new CreateAccountFrame(getCustomer(), "Saving");
                }else{
                    ((CustomerFrame)GuiUtil.getFrame(CustomerHomepagePanel.this)).setContextPanel(new MultiCurrAccountPanel(getCustomer().getAccount("Saving")));
                }
            }
        });
        add(saving);

        JButton loan = new JButton(StringUtil.getHomepageButtonString(getCustomer(),"Loan"));
        loan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCustomer().getAccount("Loan") == null){
                    GuiUtil.getFrame(CustomerHomepagePanel.this).dispose();
                    new CreateAccountFrame(getCustomer(), "Loan");
                }else{
                    ((CustomerFrame)GuiUtil.getFrame(CustomerHomepagePanel.this)).setContextPanel(new MultiCurrAccountPanel(getCustomer().getAccount("Loan")));
                }
            }
        });
        add(loan);

        JButton security = new JButton(StringUtil.getHomepageButtonString(getCustomer(),"Security"));
        security.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCustomer().getAccount("Security") == null){
                    GuiUtil.getFrame(CustomerHomepagePanel.this).dispose();
                    new CreateAccountFrame(getCustomer(), "Security");
                }else{
                    ((CustomerFrame)GuiUtil.getFrame(CustomerHomepagePanel.this)).setContextPanel(new MultiCurrAccountPanel(getCustomer().getAccount("Security")));
                }
            }
        });
        add(security);
    }

    public static void main(String[] args) {
        TestPanel.test(new CustomerHomepagePanel(new Customer()));
    }
}
