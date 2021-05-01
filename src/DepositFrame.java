import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositFrame extends PopupFrame {
    private Account account;
    public DepositFrame(Account account) {
        super(account.toString() + " Deposit");
        this.account = account;
        setFrame();
        setVisible(true);
    }


    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(3,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);

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
                    String message = ((CanDeposit) account).deposit(amount, cur);
                    DepositFrame.this.dispose();
                    new MessageFrame("Deposit Success", message);
                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(DepositFrame.this)).getContextPanel()).repaintPanel();

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

//    public static void main(String[] args) {
//        new DepositFrame();
//    }
}
