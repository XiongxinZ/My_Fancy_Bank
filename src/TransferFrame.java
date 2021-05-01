import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TransferFrame extends PopupFrame{

    private Account account;
//    private String type;
    private int transType;

    public TransferFrame(Account account, int tranType) {
        super(account.toString() + " Transfer");
        this.account = account;
//        this.type = type;
        this.transType = tranType;
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


        JLabel toLabel = new JLabel("ToWhom: ");
        jPanel.add(toLabel);

        JLabel to0;
        JTextField to1 = null;
        if (transType == 0){
            to0 = new JLabel(account.getCustomer().getEmail());
            jPanel.add(to0);
        }else{
            to1 = new JTextField("example@mail.com");
            jPanel.add(to1);
        }

        JLabel toAccountLabel = new JLabel("ToAccount: ");
        jPanel.add(toAccountLabel);

        JComboBox<String> toAccount = new JComboBox<>(SystemDatabase.accType);
//        JLabel balance = new JLabel("0.0");
        jPanel.add(toAccount);

        JLabel amountLabel = new JLabel("Amount: ");
        jPanel.add(amountLabel);

        JTextField amountText = new JTextField();
        jPanel.add(amountText);

        JButton ok = new JButton("OK");
        JTextField finalTo = to1;
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountText.getText().trim());
                    String cur = ((String) box.getSelectedItem()).trim();
                    String accType = (String) toAccount.getSelectedItem();
                    if (transType == 0){
                        String message = ((CanTransferWithin) account).transfer(accType, amount, cur);
                        TransferFrame.this.dispose();
                        new MessageFrame("Transfer Success", message);
                    }else{
                        String email = finalTo.getText().trim();
                        String message = ((CanTransferToOthers) account).transfer(email, accType, amount, cur);
                        TransferFrame.this.dispose();
                        new MessageFrame("Transfer Success", message);
                    }
                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(TransferFrame.this)).getContextPanel()).repaintPanel();

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
}
