import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ExchangeFrame extends PopupFrame {
    private Account account;
    public ExchangeFrame(Account account) {
        super(account.toString() + " exchange");
        this.account = account;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel fromLabel = new JLabel("From Currency: ");
        jPanel.add(fromLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);

        JLabel balanceLabel = new JLabel("From Balance: ");
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


        JLabel toLabel = new JLabel("To Currency: ");
        jPanel.add(toLabel);

        JComboBox<String> box2 = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box2);

        JLabel balanceToLabel = new JLabel("To Balance: ");
        jPanel.add(balanceToLabel);

        JLabel balanceTo = new JLabel(Double.toString(account.getAmount((String) box2.getSelectedItem())));
        jPanel.add(balanceTo);

        box2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                balanceTo.setText(Double.toString(account.getAmount((String) box2.getSelectedItem())));
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
                    String from = ((String) box.getSelectedItem()).trim();
                    String to = ((String) box2.getSelectedItem()).trim();
                    String message = ((CanExchange) account).exchange(from, to, amount);
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
//                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(ExchangeFrame.this)).getContextPanel()).repaintPanel();

                    ExchangeFrame.this.dispose();
                    new MessageFrame("Change Success", message);

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
                ExchangeFrame.this.dispose();
                new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
