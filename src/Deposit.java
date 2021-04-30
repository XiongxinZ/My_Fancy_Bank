public class Deposit extends Transaction{
//    private String currency = "USD";
    public Deposit(Account to, double amount) {
        super(null, to, amount);
    }

    public Deposit(Account to, double amount, String currency) {
        super(null, to, amount,currency);
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
            AccountDao.updateAccountMoney(savingAccount,getCurrencyTo());
            TransactionDao.insertTransaction(this);
            ret = "Deposit " + getAmount() + ", balance $" + savingAccount.getAmount(getCurrencyTo());
        }else if (getTo() instanceof CheckingAccount){
//            ret = ((CheckingAccount) getTo()).deposit(getAmount(), currency);
//            TransactionDao.insertTransaction(this);

            CheckingAccount checkingAccount = (CheckingAccount) getTo();
            checkingAccount.addCurrency(getAmount(), getCurrencyTo());
            AccountDao.updateAccountMoney(checkingAccount,getCurrencyTo());
            TransactionDao.insertTransaction(this);
            ret = "Deposit " + getAmount() + ", balance $" + checkingAccount.getAmount(getCurrencyTo());
        }else{
            ret = getTo().toString() + " can't deposit.";
        }
        return ret;
    }
}
