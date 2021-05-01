import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TakeLoanFrame  extends PopupFrame{
    private Account account;
    public TakeLoanFrame(Account account) {
        super("Take Loan");
        this.account = account;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel collLabel = new JLabel("Collateral: ");
        jPanel.add(collLabel);

        JComboBox<Collateral> collateral = new JComboBox<>(CollateralDao.selectUnusedCollateralList(
                account.getCustomer()).toArray(new Collateral[0]));
        jPanel.add(collateral);

        JLabel typeLabel = new JLabel("Currency: ");
        jPanel.add(typeLabel);

        JComboBox<String> box = new JComboBox<>(new String[]{"USD", "CNY", "JPY"});
        jPanel.add(box);

        JLabel valueLabel = new JLabel("Value: ");
        jPanel.add(valueLabel);


        double val = collateral.getSelectedItem() == null ? 0.0: ((Collateral)collateral.getSelectedItem()).getPrice() *
                ConfigUtil.getConfigDouble("USDTo"+box.getSelectedItem());
        JLabel value = new JLabel(Double.toString(val));
        jPanel.add(value);

        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                double val = collateral.getSelectedItem() == null ? 0.0: ((Collateral)collateral.getSelectedItem()).getPrice() *
                        ConfigUtil.getConfigDouble("USDTo"+box.getSelectedItem());
                value.setText(Double.toString(val));
            }
        });

        collateral.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                double val = collateral.getSelectedItem() == null ? 0.0: ((Collateral)collateral.getSelectedItem()).getPrice() *
                        ConfigUtil.getConfigDouble("USDTo"+box.getSelectedItem());
                value.setText(Double.toString(val));
            }
        });


        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Collateral collateral1 = (Collateral) collateral.getSelectedItem();
                    String cur = ((String) box.getSelectedItem()).trim();
                    String message = ((LoanAccount) account).takeLoan(collateral1, cur);
                    TakeLoanFrame.this.dispose();
                    new MessageFrame("Repayment Success", message);
                    new CustomerFrame(account.getCustomer()).setContextPanel(new MultiCurrAccountPanel(account));
//                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(TakeLoanFrame.this)).getContextPanel()).repaintPanel();
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
