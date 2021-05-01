import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MultiCurrAccountPanel extends CustomerContentPanel{
    private Account account;

    private JPanel infoPanel;
    private JPanel operationPanel;
    public MultiCurrAccountPanel(Account account) {
        super(account.getCustomer());
        this.account = account;
        setPanel();
    }

    private void setPanel(){
        setLayout(new GridLayout(2,1));

        infoPanel = infoPanel();
        operationPanel = operationPanel();
        add(infoPanel);
        add(operationPanel);
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
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new TransferFrame(account,1);
                }
            });
            jp.add(transferToOthers);
        }

        if (account instanceof CanTransferWithin){
            JButton transferToAccount = new JButton("Transfer To My Own Account");
            transferToAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new TransferFrame(account,0);
                }
            });
            jp.add(transferToAccount);
        }


        if (account instanceof CanWithdraw){
            JButton withdraw = new JButton("Withdraw");
            withdraw.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
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
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
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
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new ExchangeFrame(account);
                }
            });

            jp.add(exchange);
        }

        if (account instanceof LoanAccount){
            JButton repayment = new JButton("Repayment");
            repayment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new RepaymentFrame(account);
                }
            });
            jp.add(repayment);

            JButton takeLoan = new JButton("Take Loan");
            takeLoan.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new TakeLoanFrame(account);
                }
            });
            jp.add(takeLoan);

            JButton uploadCollateral = new JButton("Upload Collateral");
            uploadCollateral.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new UploadCollateralFrame(account.getCustomer());
                }
            });
            jp.add(uploadCollateral);
        }

        if (account instanceof SecurityAccount){
            JButton buy = new JButton("Buy Stock");
            buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<StockInfo> stockInfos = StockDao.selectStockInfoList();
                    if (stockInfos.size() == 0){
                        new MessageFrame("Error", "No Stock available");
                    }else{
                        GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                        new BuyStockFrame((SecurityAccount) account, stockInfos);
                    }
                }
            });
            jp.add(buy);
            JButton sell = new JButton("Sell Stock");
            sell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<StockInfo> list = StockDao.selectPositionList(account.getCustomer());
                    if (list.size() == 0){
                        new MessageFrame("Error", "No Stock available");
                    }else{
                        GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                        new SellStockFrame((SecurityAccount) account, list);
                    }
                }
            });
            jp.add(sell);
        }

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CustomerFrame)GuiUtil.getFrame(MultiCurrAccountPanel.this)).setContextPanel(new CustomerHomepagePanel(getCustomer()));
            }
        });
        jp.add(back);

        return jp;
    }

    public void repaintPanel(){
        remove(infoPanel);
        remove(operationPanel);
        infoPanel = infoPanel();
        add(infoPanel);
        add(operationPanel);
        updateUI();
    }
}
