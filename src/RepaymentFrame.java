import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RepaymentFrame extends PopupFrame{
    private Account account;
    public RepaymentFrame(Account account) {
        super("Repayment");
        this.account = account;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel targetLabel = new JLabel(Repayment.target+" Balance: ");
        jPanel.add(targetLabel);

        JLabel target = new JLabel(Double.toString(account.getAmount(Repayment.target)));
        jPanel.add(target);

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                target.setText(Double.toString(account.getAmount((String) box.getSelectedItem())));
            }
        });
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
                    String message = ((LoanAccount) account).repayment(amount, cur);
                    RepaymentFrame.this.dispose();
                    new MessageFrame("Repayment Success", message);
                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(RepaymentFrame.this)).getContextPanel()).repaintPanel();
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
