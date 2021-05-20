import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        setLayout(new GridLayout(0,1));

        infoPanel = infoPanel();
        operationPanel = operationPanel();
        add(infoPanel);
        if (account instanceof SecurityAccount){
            JTabbedPane jTabbedPane = new JTabbedPane();

            jTabbedPane.addTab("My Stock Position", new StockPositionPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(0, KeyEvent.VK_0);

            jTabbedPane.addTab("Bank Stock Pool", new StockListPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(1, KeyEvent.VK_0);

            jTabbedPane.addTab("My Stock Profit", new StockProfitPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(2, KeyEvent.VK_0);

            add(jTabbedPane);
        }

        if (account instanceof LoanAccount){
            JTabbedPane jTabbedPane = new JTabbedPane();

            jTabbedPane.addTab("My Loan", new LoanPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(0, KeyEvent.VK_0);

            jTabbedPane.addTab("My Collateral", new CollateralPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(1, KeyEvent.VK_0);

            jTabbedPane.addTab("My Collateral Request", new CollateralRequestPanel(getCustomer()));
            jTabbedPane.setMnemonicAt(2, KeyEvent.VK_0);

            add(jTabbedPane);
//            add(new LoanPanel(getCustomer()));
        }
        add(operationPanel);
    }

    private JPanel infoPanel(){
        JPanel jp = new JPanel(new GridLayout(0,1,0,0));
        jp.add(new JLabel(new ImageIcon("img/back"+ new Random().nextInt(3) +".jpeg")));
        jp.add(new JLabel("<html><b><em>"+account.toString()+"</em></b>", JLabel.CENTER ));
        jp.add(new JLabel("<html>Balance: <b>USD:</b> " + account.getAmount("USD") +
                "  <b>CNY</b>: " + account.getAmount("CNY") +
                "  <b>JPY</b>: " + account.getAmount("JPY") , JLabel.CENTER ));
        if (account instanceof SecurityAccount){
            jp.add(new JLabel("<html><font color=\"green\">Realized Profit: </font>"+getLabel(((SecurityAccount) account).getProfit()), JLabel.CENTER));
            jp.add(new JLabel("<html>Stock Position"+getLabel(((SecurityAccount) account).getStockAmount()),JLabel.CENTER));
        }

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
                    List<CustomerStock> list = StockDao.selectOwnedInfoList(account.getCustomer());
                    if (list.size() == 0){
                        new MessageFrame("Error", "No Stock available");
                    }else{
                        GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                        new SellStockFrame((SecurityAccount) account, list);
                    }
                }
            });
            jp.add(sell);

            JButton transferIn = new JButton("Transfer In");
            transferIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new TransferInFrame((SecurityAccount) account);
                }
            });
            jp.add(transferIn);

            JButton transferOut = new JButton("Transfer Out");
            transferOut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GuiUtil.getFrame(MultiCurrAccountPanel.this).dispose();
                    new TransferOutFrame((SecurityAccount) account);
                }
            });
            jp.add(transferOut);
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

    private String getLabel(HashMap<String, Double> valMap){
        String ret = "";
        for (Map.Entry<String, Double> stringDoubleEntry : valMap.entrySet()) {
            ret = ret + "  <b>"+stringDoubleEntry.getKey() + "</b>: "+stringDoubleEntry.getValue();
        }
        return ret;
    }
}
