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

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currencyFrom, String currencyTo, Date date, String transType) {
        this.fromWhom = fromWhom;
        this.toWhom = toWhom;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.transType = transType;
    }

    public AbstractTransaction(Account from, Account to, double amount, String currencyFrom, String currencyTo, Date date, String transType) {
        this.from = from;
        this.to = to;
        this.fromWhom = from == null?null: from.getCustomer().getId();
        this.toWhom = to==null?null:to.getCustomer().getId();
        this.fromAccount = from==null? null:from.getAccountType();
        this.toAccount = to==null?null:to.getAccountType();
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.transType = transType;
    }

    public AbstractTransaction(Account from, Account to, double amount, String currencyFrom, String currencyTo,String transType) {
        this(from,to,amount,currencyFrom,currencyTo,new Date(), transType);
    }
    public AbstractTransaction(Account from, Account to, double amount, String currency,String transType) {
        this(from,to,amount,currency,currency,transType);
    }

    public AbstractTransaction(Account from, String toWhom, String toAccount, double amount, String currency, Date date,String transType) {
        this.from = from;
        this.fromWhom = from.getCustomer().getId();
        this.toWhom = toWhom;
        this.fromAccount = from.getAccountType();
        this.toAccount = toAccount;
        this.transTime = date;
        this.amount = amount;
        this.currencyFrom = currency;
        this.currencyTo = currency;
        this.transType = transType;
    }

    public AbstractTransaction(Account from, String toWhom, String toAccount, double amount, String currency,String transType) {
        this(from,toWhom,toAccount,amount,currency,new Date(),transType);
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currencyFrom, String currencyTo, String transType) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,currencyFrom,currencyTo,new Date(),transType);
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String currency, String transType) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,currency,currency, transType);
    }

    public AbstractTransaction(Account from, Account to, double amount, String transType) {
        this(from,to,amount,"USD",transType);
    }

    public AbstractTransaction(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount, String transType) {
        this(fromWhom,toWhom,fromAccount,toAccount,amount,"USD", transType);
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
        return from == null? null:from.getAmount(currencyFrom);
    }

    public Double getToBalance() {
        return to == null? null:to.getAmount(currencyTo);
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract String execute();

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }



}
