public class Repayment extends AbstractTransaction {

    public static final String target = "Checking";
    private Loan loan;
    public Repayment(Loan loan,Account to, double amount, String curr) {
        super(to.getCustomer().getAccount(target),to, amount, curr,"Repayment");
        this.loan = loan;
    }

    @Override
    public String execute() {
        String ret;
        assert getTo() instanceof LoanAccount;
        if (loan.getBalance() < getAmount()){
            setAmount(loan.getBalance());
        }

        if (getFrom().getAmount(getCurrencyFrom()) >= getAmount()){
            getFrom().removeCurrency(getAmount(), getCurrencyFrom());
            loan.repayment(getAmount());
            AccountDao.updateAccountMoney(getFrom(), getCurrencyFrom());
            AccountDao.updateAccountMoney(getTo(), getCurrencyTo());
            TransactionDao.insertTransaction(this);
            if (loan.getBalance()<0){
                LoanDao.updateLoanBalance(loan);
                ret = "Repay " + getAmount() + getCurrencyTo() + ", " +
                        getTo().getAmount(getCurrencyTo()) + getCurrencyTo() + " remaining";
            }else{
                ((LoanAccount) getTo()).getLoanPool().remove(loan.getId());
                LoanDao.removeLoan(loan);
                loan.getCollateral().setUsed(false);
                CollateralDao.updateUsed(loan.getCollateral());
                ret = "Repay " + getAmount() + getCurrencyTo() + ", clear!!!";
            }
        }else{
            ret = "You don't have enough money in " + target + " account. Please deposit first";
        }
        return ret;
    }
}
