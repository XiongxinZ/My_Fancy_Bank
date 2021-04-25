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
        if (getTo() instanceof SavingAccount){
            return ((SavingAccount) getTo()).deposit(getAmount(), currency);
        }else if (getTo() instanceof CheckingAccount){
            return ((CheckingAccount) getTo()).deposit(getAmount(), currency);
        }else{
            return getTo().toString() + " can't deposit.";
        }
    }
}
