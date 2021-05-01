import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class WithdrawFrame extends PopupFrame {
    private Account account;
    public WithdrawFrame(Account account) {
        super(account.toString() + " Withdraw");
        this.account = account;
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


        JLabel balanceLabel = new JLabel("Balance: ");
        jPanel.add(balanceLabel);

        JLabel balance = new JLabel(Double.toString(account.getAmount((String) box.getSelectedItem())));
//        JLabel balance = new JLabel("0.0");
        jPanel.add(balance);

        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                balance.setText(Double.toString(account.getAmount((String) box.getSelectedItem())));
            }
        });

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
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
//                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(WithdrawFrame.this)).getContextPanel()).repaintPanel();

                    WithdrawFrame.this.dispose();
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
                new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }

}
