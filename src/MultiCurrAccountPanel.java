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
        setLayout(new GridLayout(1,1));

        add(infoPanel());
        add(operationPanel());
    }

    private JPanel infoPanel(){
        JPanel jp = new JPanel(new GridLayout(0,1));
        jp.add(new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("img/back0.jpeg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(),getHeight(),
                        icon.getImageObserver());
            }
        });
        jp.add(new JLabel("<html><b><em>"+account.toString()+"</em></b><br>"+
                "USD: " + account.getAmount("USD") + "<br>" +
                "CNY: " + account.getAmount("CNY") + "<br>" +
                "JPY: " + account.getAmount("JPY") + "<br>" ));

        return jp;
    }

    private JPanel operationPanel(){
        JPanel jp = new JPanel();
        JButton transferToOthers = new JButton("Transfer To Other Customer");
        JButton transferToAccount = new JButton("Transfer To My Own Account");
        JButton withdraw = new JButton("Withdraw");
        JButton deposit = new JButton("Deposit");
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JButton transferToOther = new JButton("Transfer To Others");

        return jp;
    }
}
