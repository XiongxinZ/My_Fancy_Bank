import java.util.HashMap;

public class Account implements Modifiable {

    private HashMap<String, Double> amount = new HashMap<>();
    private Customer customer;
    private String accountType;
    private String id;

    private boolean isDirty = false;

    public Account(Customer customer, String currency, double amount, String accountType) {
        this.customer = customer;
        this.accountType = accountType;
        this.amount.put("USD",0.0);
        this.amount.put("CNY",0.0);
        this.amount.put("JPY",0.0);
        addCurrency(amount, currency);
        id = Long.toString(Long.parseLong(customer.getId()) * 31L + accountType.hashCode());
    }

    public Account(Customer customer, double amount, String accountType) {
        this.customer = customer;
        this.accountType = accountType;
        this.amount.put("USD",0.0);
        this.amount.put("CNY",0.0);
        this.amount.put("JPY",0.0);
        addCurrency(amount);
        id = Long.toString(Long.parseLong(customer.getId()) * 31L + accountType.hashCode());
    }

    public Account(Customer customer, String accountType) {
        this(customer, 0.0, accountType);
    }

    public Account(Customer customer, double amount) {
        this(customer, amount, "Saving");
    }

    public Account(Customer customer) {
        this(customer, 0.0, "Saving");
    }

    public void setId(String id) {
        this.id = id;
    }

    // Transfer to another account
    public String transfer(Account account, double val){
        account.addCurrency(val);
        this.removeCurrency(val);
        return "Transfer " + val + " from "+ toString() +" account to "+ account.toString()+"account.";
    }

    // Transfer to customer's another account
    public String transfer(String account, double val, String currency){
//        if (customer.getAccount(account) == null){
//            return "Sorry you don't have the " + account + " account";
//        }
        return new Transfer(this, customer.getAccount(accountType), val, currency).execute();

    }

    public void consume(double val){
        assert  amount.containsKey("USD") && amount.get("USD") >= val;
        amount.put("USD", amount.get("USD")- val);
    }

    protected void addCurrency(double val){
        addCurrency(val, "USD");
    }

    protected void addCurrency(double val, String currency){
        if (amount.containsKey(currency)){
            amount.put(currency, amount.get(currency) + val);
        }else{
            amount.put(currency, val);
        }
    }

    protected void removeCurrency(double val){
        removeCurrency(val, "USD");
    }

    protected void removeCurrency(double val, String currency){
        assert amount.containsKey(currency);
        assert  amount.get(currency) - val >= 0;
        if (amount.containsKey(currency)){
            amount.put(currency, amount.get(currency) - val);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount.get("USD");
    }

    public String getId() {
        return id;
    }

    public double getAmount(String currency) {
        return amount.get(currency) == null ? 0:amount.get(currency);
    }

    public void setAmount(double amount) {
        this.amount.put("USD", this.amount.get("USD")+amount);
    }
    public void setAmount(double amount, String curr) {
        this.amount.put(curr, amount);
    }

    @Override
    public String toString() {
        return customer.getName() + "'s " + accountType + " Account";
    }

    @Override
    public void markDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }
}
