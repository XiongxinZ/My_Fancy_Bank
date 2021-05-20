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
            AccountDao.getInstance().updateAccountMoney(savingAccount, getCurrencyFrom());
            TransactionDao.getInstance().insertTransaction(this);
            ret =  "Withdraw " + getAmount() +getCurrencyFrom()+
                    "\nBalance " + savingAccount.getAmount(getCurrencyFrom())+getCurrencyFrom();
        }else if (getFrom() instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) getFrom();
            setAmount(Math.min(getAmount(), getFrom().getAmount(getCurrencyFrom())/(1+ConfigUtil.getConfigDouble("CheckingRate"))));
            checkingAccount.removeCurrency(getAmount()*(1+ConfigUtil.getConfigDouble("CheckingRate")), getCurrencyFrom());
            AccountDao.getInstance().updateAccountMoney(checkingAccount,getCurrencyFrom());
            TransactionDao.getInstance().insertTransaction(this);
            ret = "Withdraw " + getAmount() +getCurrencyFrom()+
                    "\nBalance " + checkingAccount.getAmount(getCurrencyFrom())+getCurrencyFrom()+
                    "\nFee "+getAmount()*ConfigUtil.getConfigDouble("CheckingRate") + getCurrencyFrom();
        }else{
            ret = getFrom().toString() + " can't withdraw.";
        }
        return ret;
    }
}
