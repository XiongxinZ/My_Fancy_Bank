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
        if (getTo() instanceof CheckingAccount){
            ret = ((CheckingAccount) getTo()).withdraw(getAmount(), currency);
            TransactionDao.insertTransaction(this);
        }else{
            ret = getTo().toString() + " can't withdraw.";
        }
        return ret;
    }
}
