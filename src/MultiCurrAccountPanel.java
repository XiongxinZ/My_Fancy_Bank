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
        if (account instanceof CanTransferToOthers){
            JButton transferToOthers = new JButton("Transfer To Other Customer");
            transferToOthers.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            jp.add(transferToOthers);
        }

        if (account instanceof CanTransferWithin){
            JButton transferToAccount = new JButton("Transfer To My Own Account");
            transferToAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            jp.add(transferToAccount);
        }


        if (account instanceof CanWithdraw){
            JButton withdraw = new JButton("Withdraw");
            withdraw.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new WithdrawFrame(account);
                }
            });
            jp.add(withdraw);
        }


        if (account instanceof CanDeposit){
            JButton deposit = new JButton("Deposit");
            deposit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new DepositFrame(account);
                }
            });
            jp.add(deposit);
        }


        if (account instanceof CanExchange){
            JButton exchange = new JButton("Exchange");
            exchange.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ExchangeFrame(account);
                }
            });

            jp.add(exchange);
        }

        return jp;
    }
}
