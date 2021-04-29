import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountFrame extends PopupFrame{
    private Customer customer;
    private String type;
    public CreateAccountFrame(Customer customer, String type) {
        super("Create Account");
        this.customer = customer;
        this.type = type;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        setLayout(new GridLayout(4,1));
        JLabel label = new JLabel("How to Pay Account Fee($10)", JLabel.CENTER);
        add(label);

        JButton button1 = new JButton("Cash(Deposit)");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DepositTempFrame(customer, type);
            }
        });

        JButton button2;
        String targetAccount;
        if (type.equalsIgnoreCase("Saving")){
            button2 = new JButton("Checking Account");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (customer.getAccount("Checking").getAmount() >= ConfigUtil.getConfigInt("AccountFee")){
                        dispose();
                        String message = SavingAccount.createAccountFromAccount(customer);
                        new CustomerFrame(customer);
                        new MessageFrame("Success Info",message);
                    }else{
                        new MessageFrame("Error Info", "You don't have enough money");
                    }
                }
            });
        }else{
            button2 = new JButton("Saving Account");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (customer.getAccount("Saving").getAmount() >= ConfigUtil.getConfigInt("AccountFee")){
                        CreateAccountFrame.this.dispose();
                        String message = CheckingAccount.createAccountFromAccount(customer);
                        new CustomerFrame(customer);
                        new MessageFrame("Success Info",message);
                    }else{
                        new MessageFrame("Error Info", "You don't have enough money");
                    }
                }
            });
        }

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountFrame.this.dispose();
                new CustomerFrame(customer);
            }
        });

        add(button1);
        add(button2);
        add(back);
    }
}
