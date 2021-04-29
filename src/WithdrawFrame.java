import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawFrame extends PopupFrame {
    private Account account;
    public WithdrawFrame(Account account) {
        super(account.toString() + " Withdraw");
        this.account = account;
        setFrame();
        setVisible(true);
    }

    public WithdrawFrame() {
        super(" Withdraw");
//        this.account = account;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(4,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);


        JLabel balanceLabel = new JLabel("Balence: ");
        jPanel.add(balanceLabel);

        JLabel balance = new JLabel(Double.toString(account.getAmount((String) box.getSelectedItem())));
//        JLabel balance = new JLabel("0.0");
        jPanel.add(balance);


        JLabel amountLabel = new JLabel("Amount: ");
        jPanel.add(amountLabel);

        JTextField amountText = new JTextField();
        jPanel.add(amountText);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountText.getText().trim());
                    String cur = ((String) box.getSelectedItem()).trim();
                    String message = ((CanWithdraw) account).withdraw(amount, cur);
                    new MessageFrame("Deposit Success", message);
                }catch (NumberFormatException e1){
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        jPanel.add(ok);

        JButton back = new JButton("Cancel");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jPanel.add(back);

        add(jPanel);
    }

    public static void main(String[] args) {
        new DepositFrame();
    }
}
