import java.util.Date;

public abstract class AbstractTransaction implements Transaction {
    private String fromWhom;
    private String toWhom;

    private String fromAccount;
    private String toAccount;

    private Account from;
    private Account to;

    private Date transTime;
    private double amount;

    private String transType;

    private String currencyFrom;
    private String currencyTo;

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currencyFrom, String currencyTo, Date date) {
        this.fromWhom = fromWhom;
        this.toWhom = toWhom;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public AbstractTransaction(Account from, Account to, double amount, String currencyFrom, String currencyTo, Date date) {
        this.from = from;
        this.to = to;
        this.fromWhom = from.getCustomer().getName();
        this.toWhom = to.getCustomer().getName();
        this.fromAccount = from.getAccountType();
        this.toAccount = to.getAccountType();
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public AbstractTransaction(Account from, Account to, double amount, String currencyFrom, String currencyTo) {
        this(from,to,amount,currencyFrom,currencyTo,new Date());
    }
    public AbstractTransaction(Account from, Account to, double amount, String currency) {
        this(from,to,amount,currency,currency);
    }

    public AbstractTransaction(Account from, String toWhom, String toAccount, double amount, String currency, Date date) {
        this.from = from;
        this.fromWhom = from.getCustomer().getName();
        this.toWhom = toWhom;
        this.fromAccount = from.getAccountType();
        this.toAccount = toAccount;
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currency;
        this.currencyTo = currency;
    }

    public AbstractTransaction(Account from, String toWhom, String toAccount, double amount, String currency) {
        this(from,toWhom,toAccount,amount,currency,new Date());
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currencyFrom, String currencyTo) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,currencyFrom,currencyTo,new Date());
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currency) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,currency,currency);
    }

    public AbstractTransaction(Account from, Account to, double amount) {
        this(from,to,amount,"USD");
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,"USD");
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

    public String getFromWhom() {
        return fromWhom;
    }

    public String getToWhom() {
        return toWhom;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getTransType() {
        return transType;
    }

    public Date getTransTime() {
        return transTime;
    }

    public Double getFromBalance() {
        return from == null? null:from.getAmount();
    }

    public Double getToBalance() {
        return to == null? null:to.getAmount();
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public abstract String execute();

}