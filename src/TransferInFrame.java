import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TransferInFrame extends PopupFrame{
    private SecurityAccount account;
    public TransferInFrame(SecurityAccount account) {
        super("Transfer In");
        this.account = account;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){

        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);

        JLabel savingBalanceLabel = new JLabel("Saving Balance: ");
        jPanel.add(savingBalanceLabel);

        JLabel savingBalance = new JLabel(Double.toString(
                account.getCustomer().getAccount("Saving").getAmount((String) box.getSelectedItem())));
        jPanel.add(savingBalance);

        JLabel balanceLabel = new JLabel("Balance: ");
        jPanel.add(balanceLabel);

        JLabel balance = new JLabel(Double.toString(account.getAmount((String) box.getSelectedItem())));
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

                    String message = account.transferIn(amount, cur);
                    TransferInFrame.this.dispose();
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
                    new MessageFrame("Transfer Success", message);

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
