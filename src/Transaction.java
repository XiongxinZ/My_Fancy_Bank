import java.util.Date;

public abstract class Transaction{
    private String fromWhom;
    private String toWhom;

    private String fromAccount;
    private String toAccount;

    private Account from;
    private Account to;

    private String transTime;
    private double amount;

    private String transType;

    public Transaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount) {
        this.fromWhom = fromWhom;
        this.toWhom = toWhom;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transTime = new Date().toString();
        this.amount = amount;
    }

    public Transaction(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.fromWhom = from.getCustomer().getName();
        this.toWhom = to.getCustomer().getName();
        this.fromAccount = from.getAccountType();
        this.toAccount = to.getAccountType();
        this.transTime = new Date().toString();
        this.amount = amount;
    }

    public String showInfo(){
        return transTime + ": " + fromWhom + "'s " + fromAccount + " transfer $" + amount + " to " + toWhom + "'s " + toAccount;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransTime() {
        return transTime;
    }

    public abstract String execute();
}
