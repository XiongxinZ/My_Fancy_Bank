public class Repayment extends AbstractTransaction {

    public static final String target = "Saving";
    private Loan loan;
    public Repayment(Loan loan,Account to, double amount, String curr) {
        super(to.getCustomer().getAccount(ConfigUtil.getConfig("LoanTarget")),to, amount, curr,"Repayment");
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
            loan.repay(getAmount());
            AccountDao.getInstance().updateAccountMoney(getFrom(), getCurrencyFrom());
            AccountDao.getInstance().updateAccountMoney(getTo(), getCurrencyTo());
            TransactionDao.getInstance().insertTransaction(this);
            if (loan.getBalance()<0){
                LoanDao.getInstance().updateLoanBalance(loan);
                ret = "Repay " + "<font color=\"red\">"+PrintUtil.printDouble(getAmount()) +"</font>"+ getCurrencyTo() + ", " +
                        getTo().getAmount(getCurrencyTo()) + getCurrencyTo() + " remaining";
            }else{
                ((LoanAccount) getTo()).getLoanPool().remove(loan.getId());
                LoanDao.getInstance().removeLoan(loan);
                loan.getCollateral().setUsed(false);
                CollateralDao.getInstance().updateUsed(loan.getCollateral());
                ret = "Repay " +"<font color=\"red\">"+PrintUtil.printDouble( getAmount())+"</font>" + getCurrencyTo() + ", clear!!!";
            }
        }else{
            ret = "You don't have enough money in " + ConfigUtil.getConfig("LoanTarget") + " account. Please deposit first";
        }
        return ret;
    }
}
