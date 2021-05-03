import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositTempFrame extends PopupFrame{
    private Customer customer;
    private String type;

    public DepositTempFrame(Customer customer, String type) {
        super("Pay Fee");
        this.customer = customer;
        this.type = type;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        setLayout(new GridLayout(4,1));
        add(new JLabel("Deposit USD and then create the account:", JLabel.CENTER));
        JPanel jp = new JPanel(new GridLayout(1,2));
        jp.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        JLabel label = new JLabel("Amount: ");
        JTextField jTextField = new JTextField();
        jp.add(label);
        jp.add(jTextField);
        add(jp);
        JButton submit = new JButton("Confirm");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double val = Double.parseDouble(jTextField.getText().trim());
                    if (type.equalsIgnoreCase("Saving")){
                        DepositTempFrame.this.dispose();
                        new MessageFrame("Success Info",SavingAccount.createAccountFromCash(customer, val));
                    }else{
                        DepositTempFrame.this.dispose();
                        new MessageFrame("Success Info", CheckingAccount.createAccountFromCash(customer, val));
                    }
                } catch (NumberFormatException numberFormatException) {
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        add(submit);

        JButton back = new JButton("Back");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateAccountFrame(customer,type);
            }
        });
        add(back);
    }
}
