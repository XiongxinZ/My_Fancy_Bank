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

        if (type.equals("Saving") || type.equals("Checking")){
            JButton button1 = new JButton("Cash(Deposit)");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateAccountFrame.this.dispose();
                    new DepositTempFrame(customer, type);
                }
            });
            add(button1);
        }


        JButton button2;
        if (type.equalsIgnoreCase("Saving")){
            button2 = new JButton("Checking Account");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (customer.hasAccount("Checking")){
                        if (customer.getAccount("Checking").getAmount() >= ConfigUtil.getConfigInt("AccountFee")){
                            String message = SavingAccount.createAccountFromAccount(customer);
                            CreateAccountFrame.this.dispose();
                            new CustomerFrame(customer);
                            new MessageFrame("Success Info",message);
                        }else{
                            new MessageFrame("Error Info", "You don't have enough money");
                        }
                    }else{
                        new MessageFrame("Error Info", "You don't Checking account");
                    }

                }
            });
        }else{
            button2 = new JButton("Saving Account");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (customer.hasAccount("Saving")){
                        if (customer.getAccount("Saving").getAmount() >= ConfigUtil.getConfigInt("AccountFee")){
                            String message = switch (type) {
                                case "Checking" -> CheckingAccount.createAccountFromAccount(customer);
                                case "Security" -> SecurityAccount.createAccountFromAccount(customer);
                                case "Loan" -> LoanAccount.createAccountFromAccount(customer);
                                default -> "Success.";
                            };
                            CreateAccountFrame.this.dispose();
                            new CustomerFrame(customer);
                            new MessageFrame("Success Info",message);
                        }else{
                            new MessageFrame("Error Info", "You don't have enough money");
                        }
                    }else{
                        new MessageFrame("Error Info", "You don't Saving account");
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

        add(button2);
        add(back);
    }
}
