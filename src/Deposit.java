public class Deposit extends Transaction{
    private String currency = "USD";
    public Deposit(Account to, double amount) {
        super(null, to, amount);
    }

    public Deposit(Account to, double amount, String currency) {
        super(null, to, amount);
        this.currency = currency;
    }

    @Override
    public String showInfo() {
        return getTransTime() + ": " + "Deposit " + getAmount() +" "+ currency +" to " + getTo().toString();
    }

    @Override
    public String execute() {
        assert getTo() instanceof SavingAccount || getTo() instanceof CheckingAccount;
        String ret;
        if (getTo() instanceof SavingAccount){
            ret =  ((SavingAccount) getTo()).deposit(getAmount(), currency);
            TransactionDao.insertTransaction(this);
        }else if (getTo() instanceof CheckingAccount){
            ret = ((CheckingAccount) getTo()).deposit(getAmount(), currency);
            TransactionDao.insertTransaction(this);
        }else{
            ret = getTo().toString() + " can't deposit.";
        }
        return ret;
    }
}
