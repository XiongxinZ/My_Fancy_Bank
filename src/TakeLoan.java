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
        getTo().removeCurrency(getAmount(),getCurrencyTo());
        getFrom().addCurrency(getAmount(), getCurrencyFrom());
        collateral.setUsed(true);
        Loan newLoan = new Loan(getFrom().getCustomer(), collateral, collateral.getPrice(), getCurrencyFrom());
        ((LoanAccount)getFrom()).getLoanPool().put(newLoan.getId(), newLoan);
        AccountDao.updateAccountMoney(getFrom(),getCurrencyFrom());
        AccountDao.updateAccountMoney(getTo(),getCurrencyTo());
        LoanDao.insertLoan(newLoan);
        CollateralDao.updateUsed(collateral);
        TransactionDao.insertTransaction(this);
        return "Success! " + ConfigUtil.getConfig("LoanTarget") + "Account add " + getAmount() + getCurrencyTo();
    }
}
