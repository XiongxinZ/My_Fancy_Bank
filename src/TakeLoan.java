import java.io.Serial;

public class TakeLoan extends Transaction{

    @Serial
    private static final long serialVersionUid = -6531033077785053223L;
//    private Customer customer;
    private Collateral collateral;
//    private String curr;

    public TakeLoan(Account from, Collateral collateral, String curr) {
        super(from, from.getCustomer().getAccount(ConfigUtil.getConfig("LoanTarget")),
                collateral.getPrice()* ConfigUtil.getConfigDouble("USDTo"+curr), curr);
        this.collateral = collateral;
//        this.curr = curr;
    }


    @Override
    public String execute() {
        getTo().addCurrency(getAmount(),getCurrencyTo());
        getFrom().addCurrency(getAmount(), getCurrencyFrom());
        AccountDao.updateAccountMoney(getFrom(),getCurrencyFrom());
        AccountDao.updateAccountMoney(getTo(),getCurrencyTo());
        CollateralDao.updateUsed(collateral);
        TransactionDao.insertTransaction(this);
        return "Success! " + ConfigUtil.getConfig("LoanTarget") + "Account add " + getAmount() + getCurrencyTo();
    }
}
