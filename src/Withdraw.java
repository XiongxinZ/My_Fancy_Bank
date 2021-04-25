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
        if (getTo() instanceof CheckingAccount){
            return ((CheckingAccount) getTo()).deposit(getAmount(), currency);
        }else{
            return getTo().toString() + " can't withdraw.";
        }
    }
}
