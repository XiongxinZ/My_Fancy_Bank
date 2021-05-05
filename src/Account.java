import java.util.HashMap;

public abstract class Account{

    private HashMap<String, Double> amountTotal = new HashMap<>();
    private Customer customer;
    private String accountType;
    private String id;

    private boolean isDirty = false;

    public Account(Customer customer, String currency, double amount, String accountType) {
        this.customer = customer;
        this.accountType = accountType;
        this.amountTotal.put("USD",0.0);
        this.amountTotal.put("CNY",0.0);
        this.amountTotal.put("JPY",0.0);
        addCurrency(amount, currency);
        id = Long.toString(Long.parseLong(customer.getId()) * 31L + accountType.hashCode());
    }

    public Account(Customer customer, double amount, String accountType) {
        this.customer = customer;
        this.accountType = accountType;
        this.amountTotal.put("USD",0.0);
        this.amountTotal.put("CNY",0.0);
        this.amountTotal.put("JPY",0.0);
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

    public String close(){
        AccountDao.getInstance().delAccount(this);
        getCustomer().getAccount("Saving").removeCurrency(10);
        AccountDao.getInstance().updateAccountMoney(getCustomer().getAccount("Saving"), "USD");
        getCustomer().getAccountList().remove(getAccountType());
        return "Close "+ getAccountType()+ "Account successfully!";
    }

    public abstract boolean canClose();

    public void consume(double val){
        assert  amountTotal.containsKey("USD") && amountTotal.get("USD") >= val;
        amountTotal.put("USD", amountTotal.get("USD")- val);
    }

    protected void addCurrency(double val){
        addCurrency(val, "USD");
    }

    protected void addCurrency(double val, String currency){
        if (amountTotal.containsKey(currency)){
            amountTotal.put(currency, amountTotal.get(currency) + val);
        }else{
            amountTotal.put(currency, val);
        }
    }

    protected void removeCurrency(double val){
        removeCurrency(val, "USD");
    }

    protected void removeCurrency(double val, String currency){
        assert amountTotal.containsKey(currency);
        assert  amountTotal.get(currency) - val >= 0;
        if (amountTotal.containsKey(currency)){
            amountTotal.put(currency, amountTotal.get(currency) - val);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amountTotal.get("USD");
    }

    public String getId() {
        return id;
    }

    public double getAmount(String currency) {
        return amountTotal.get(currency) == null ? 0: amountTotal.get(currency);
    }

    public void setAmount(double amountTotal) {
        this.amountTotal.put("USD", this.amountTotal.get("USD")+ amountTotal);
    }
    public void setAmount(double amount, String curr) {
        this.amountTotal.put(curr, amount);
    }

    @Override
    public String toString() {
        return customer.getName() + "'s " + accountType + " Account";
    }

    public HashMap<String, Double> getAmountTotal() {
        return amountTotal;
    }

    //    @Override
//    public void markDirty(boolean isDirty) {
//        this.isDirty = isDirty;
//    }
//
//    @Override
//    public boolean isDirty() {
//        return isDirty;
//    }
}
