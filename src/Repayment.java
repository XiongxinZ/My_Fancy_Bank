public class Repayment extends Transaction{

    public static final String target = "Checking";

    public Repayment(Account to, double amount, String curr) {
        super(to.getCustomer().getAccount(target),to, amount, curr);
    }

    @Override
    public String execute() {
        String ret;
        assert getTo() instanceof LoanAccount;
        if (getFrom().getAmount(getCurrencyFrom()) >= getAmount()){
            getFrom().removeCurrency(getAmount(), getCurrencyFrom());
            getTo().removeCurrency(getAmount(),getCurrencyTo());
            AccountDao.updateAccountMoney(getFrom(), getCurrencyFrom());
            AccountDao.updateAccountMoney(getTo(), getCurrencyTo());
            TransactionDao.insertTransaction(this);
            ret = "Repay " + getAmount() + getCurrencyTo() + ", " +
                    getTo().getAmount(getCurrencyTo()) + getCurrencyTo() + " remaining";
        }else{
            ret = "You don't have enough money in " + target + " account. Please deposit first";
        }
        return ret;
    }
}
