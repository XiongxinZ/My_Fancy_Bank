public class Deposit extends AbstractTransaction {
//    private String currency = "USD";
    public Deposit(Account to, double amount) {
        super(null, to, amount, "Deposit");
    }

    public Deposit(Account to, double amount, String currency) {
        super(null, to, amount,currency,"Deposit");
//        this.currency = currency;
    }

    @Override
    public String showInfo() {
        return getTransTime() + ": " + "Deposit " + getAmount() +" "+ getCurrencyTo() +" to " + getTo().toString();
    }

    @Override
    public String execute() {
        assert getTo() instanceof CanDeposit;
//        assert getTo() instanceof SavingAccount || getTo() instanceof CheckingAccount;
        String ret;
        if (getTo() instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) getTo();
            savingAccount.addCurrency(getAmount(), getCurrencyTo());
            AccountDao.getInstance().updateAccountMoney(savingAccount,getCurrencyTo());
            TransactionDao.getInstance().insertTransaction(this);
            ret = "<html>Deposit " + "<font color=\"red\">"+PrintUtil.printDouble(getAmount() )+"</font>"+getCurrencyTo()+ "<br>Balance " + "<font color=\"red\">"+PrintUtil.printDouble(savingAccount.getAmount(getCurrencyTo()))+"</font>"+getCurrencyTo();
        }else if (getTo() instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) getTo();
            checkingAccount.addCurrency(getAmount(), getCurrencyTo());
            AccountDao.getInstance().updateAccountMoney(checkingAccount,getCurrencyTo());
            TransactionDao.getInstance().insertTransaction(this);
            ret = "<html>Deposit " + "<font color=\"red\">"+PrintUtil.printDouble(getAmount())+"</font>" + getCurrencyTo()  +
                    "<br>Balance " + "<font color=\"red\">"+PrintUtil.printDouble(checkingAccount.getAmount(getCurrencyTo()))+"</font>" + getCurrencyTo();
        }else{
            ret = getTo().toString() + " can't deposit.";
        }
        return ret;
    }
}
