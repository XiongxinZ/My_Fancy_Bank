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
            setAmount(Math.min(getAmount(), getFrom().getAmount(getCurrencyFrom())));
            savingAccount.removeCurrency(getAmount(), getCurrencyFrom());
            AccountDao.updateAccountMoney(savingAccount, getCurrencyFrom());
            TransactionDao.insertTransaction(this);
            ret =  "Withdraw " + getAmount() +getCurrencyFrom()+
                    ", balance " + savingAccount.getAmount(getCurrencyFrom())+getCurrencyFrom();
        }else if (getFrom() instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) getFrom();
            setAmount(Math.min(getAmount(), getFrom().getAmount(getCurrencyFrom())/(1+ConfigUtil.getConfigDouble("CheckingRate"))));
            checkingAccount.removeCurrency(getAmount()*(1+ConfigUtil.getConfigDouble("CheckingRate")), getCurrencyFrom());
            AccountDao.updateAccountMoney(checkingAccount,getCurrencyFrom());
            TransactionDao.insertTransaction(this);
            ret = "Withdraw " + getAmount() +getCurrencyFrom()+
                    "Fee "+getAmount()*ConfigUtil.getConfigDouble("CheckingRate") + getCurrencyFrom()+
                    ", balance " + checkingAccount.getAmount(getCurrencyFrom())+getCurrencyFrom();
        }else{
            ret = getFrom().toString() + " can't withdraw.";
        }
        return ret;
    }
}
