public class Withdraw extends Transaction{
    private String currency = "USD";
    public Withdraw(Account from, double amount) {
        super(from, null, amount);
    }

    public Withdraw(Account from, double amount, String currency) {
        super(from, null, amount);
        this.currency = currency;
    }

    @Override
    public String showInfo() {
        return getTransTime() + ": " + "Withdraw " + getAmount() +" "+ currency +" from " + getFrom().toString();
    }

    @Override
    public String execute() {
        assert getTo() instanceof CheckingAccount;

        String ret;
        if (getTo() instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) getFrom();
            savingAccount.removeCurrency(getAmount(), currency);
            AccountDao.updateAccountMoney(savingAccount, currency);
            TransactionDao.insertTransaction(this);
            ret =  "Withdraw $" + getAmount() + ", amount $" + getAmount();
        }else if (getTo() instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) getFrom();
            checkingAccount.removeCurrency(getAmount(), currency);
            AccountDao.updateAccountMoney(checkingAccount,currency);
            TransactionDao.insertTransaction(this);
            ret = "Withdraw $" + getAmount() + ", amount $" + getAmount();
        }else{
            ret = getTo().toString() + " can't withdraw.";
        }
        return ret;
    }
}
