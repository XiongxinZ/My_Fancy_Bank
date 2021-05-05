import java.io.Serial;

public class TakeLoan extends AbstractTransaction {

    @Serial
    private static final long serialVersionUid = -6531033077785053223L;
//    private Customer customer;
    private Collateral collateral;
//    private String curr;

    public TakeLoan(LoanAccount from, Collateral collateral, String curr) {
        super(from, from.getCustomer().getAccount(ConfigUtil.getConfig("LoanTarget")),
                collateral.getPrice()* ConfigUtil.getConfigDouble("USDTo"+curr), curr, "Take Loan");
        this.collateral = collateral;
//        this.curr = curr;
    }


    @Override
    public String execute() {
        getTo().addCurrency(getAmount(),getCurrencyTo());
        getFrom().removeCurrency(getAmount(), getCurrencyFrom());
        collateral.setUsed(true);
        Loan newLoan = new Loan(getFrom().getCustomer(), collateral, getAmount(), getCurrencyFrom());
        ((LoanAccount)getFrom()).getLoanPool().put(newLoan.getId(), newLoan);
        AccountDao.getInstance().updateAccountMoney(getFrom(),getCurrencyFrom());
        AccountDao.getInstance().updateAccountMoney(getTo(),getCurrencyTo());
        LoanDao.getInstance().insertLoan(newLoan);
        CollateralDao.getInstance().updateUsed(collateral);
        TransactionDao.getInstance().insertTransaction(this);
        return "Success! " + ConfigUtil.getConfig("LoanTarget") + "Account add " + getAmount() + getCurrencyTo();
    }
}
