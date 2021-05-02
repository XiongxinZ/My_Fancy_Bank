public class Withdraw extends AbstractTransaction {
//    private String currency = "USD";
    public Withdraw(Account from, double amount) {
        super(from, null, amount, "Withdraw");
    }

    public Withdraw(Account from, double amount, String currency) {
        super(from, null, amount,currency,"Withdraw");
//        this.currency = currency;
    }

    @Override
    public String showInfo() {
        return getTransTime() + ": " + "Withdraw " + getAmount() +" "+ getCurrencyFrom() +" from " + getFrom().toString();
    }

    @Override
    public String execute() {
        assert getFrom() instanceof CheckingAccount;

        String ret;
        if (getFrom() instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) getFrom();
            savingAccount.removeCurrency(getAmount(), getCurrencyFrom());
            AccountDao.updateAccountMoney(savingAccount, getCurrencyFrom());
            TransactionDao.insertTransaction(this);
            ret =  "Withdraw $" + getAmount() + ", balance $" + savingAccount.getAmount(getCurrencyFrom());
        }else if (getFrom() instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) getFrom();
            checkingAccount.removeCurrency(getAmount(), getCurrencyFrom());
            AccountDao.updateAccountMoney(checkingAccount,getCurrencyFrom());
            TransactionDao.insertTransaction(this);
            ret = "Withdraw $" + getAmount() + ", amount $" + checkingAccount.getAmount(getCurrencyFrom());
        }else{
            ret = getFrom().toString() + " can't withdraw.";
        }
        return ret;
    }
}
