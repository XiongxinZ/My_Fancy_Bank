import java.util.HashMap;

public class SecurityAccount extends Account implements CanTransferWithin {

    public static final String TYPE = "Security";
    private HashMap<String, CustomerStock> pool = new HashMap<>();

    static int temp = 5000;

    public SecurityAccount(Customer customer, double amount) {
        super(customer, amount,"Security");
    }

    public SecurityAccount(Customer customer) {
        super(customer,"Security");
    }

    public String buyStock(StockInfo stockInfo,  int num){
        CustomerStock stock = new CustomerStock(stockInfo,getCustomer(), num);
        return new BuyStock(this, stock).execute();
    }

    public String sellStock(){
        return null;
    }

    public HashMap<String, CustomerStock> getPool() {
        return pool;
    }

    public static String createAccountFromAccount(Customer customer){
        if (!customer.hasAccount("Saving")){
            return "You should create a Saving Account and put at least $5000 into the account";
        }else if (customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").removeCurrency(temp);
            SecurityAccount newly = new SecurityAccount(customer);
            customer.addAccount(TYPE, newly);
            customer.markDirty(true);
            AccountDao.insertAccount(newly);
            AccountDao.updateAccountMoney(customer.getAccount("Saving"),"USD");
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully. ";
        }else{
            return "Your Saving Account should have at least $5000";
        }
    }


    public String transfer(double val, Account account, String currency){
        if (val > getAmount(currency)){
            return "Sorry you only have " + getAmount() + currency + " in your " + getAccountType() + "account";
        }
        account.addCurrency(val);
        this.removeCurrency(val);
        return "Transfer " + val + " from "+ toString() +" account to "+ account.toString()+"account.";
    }

    @Override
    public String transfer(String account,double val, String curr){
        return new Transfer(this, getCustomer().getAccount(account),val , curr).execute();
    }

    @Override
    public boolean isMultiCurr() {
        return false;
    }
}
