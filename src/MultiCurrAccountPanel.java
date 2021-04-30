import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiCurrAccountPanel extends CustomerContentPanel{
    private Account account;
    public MultiCurrAccountPanel(Account account) {
        super(account.getCustomer());
        this.account = account;
        setPanel();
    }

    private void setPanel(){
        setLayout(new GridLayout(2,1));

        add(infoPanel());
        add(operationPanel());
    }

    private JPanel infoPanel(){
        JPanel jp = new JPanel();
        jp.add(new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("img/back0.jpeg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(),getHeight(),
                        icon.getImageObserver());
            }
        }, BorderLayout.NORTH);
        jp.add(new JLabel("<html><b><em>"+account.toString()+"</em></b><br>"+
                "USD: " + account.getAmount("USD") + "<br>" +
                "CNY: " + account.getAmount("CNY") + "<br>" +
                "JPY: " + account.getAmount("JPY") + "<br>" ));

        return jp;
    }

    private JPanel operationPanel(){
        JPanel jp = new JPanel(new GridLayout(0,1));
        JButton transferToOthers = new JButton("Transfer To Other Customer");
        JButton transferToAccount = new JButton("Transfer To My Own Account");

        JButton withdraw = new JButton("Withdraw");
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawFrame(account);
            }
        });
        jp.add(withdraw);

        JButton deposit = new JButton("Deposit");
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositFrame(account);
            }
        });
        jp.add(deposit);

        JButton changeCurr = new JButton("Change Currency Type");

        jp.add(changeCurr);

        jp.add(transferToAccount);
        jp.add(transferToOthers);

        return jp;
    }
}
