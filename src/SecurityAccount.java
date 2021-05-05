
import java.util.HashMap;

public class SecurityAccount extends Account{

    public static final String TYPE = "Security";

    private ValuePool<CustomerStock> stockPool = new ValuePool<>();
    private ValuePool<StockProfit> profitPool = new ValuePool<>();

    static int temp = 10;

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

    public String sellStock(CustomerStock customerStock, int num){
        CustomerStock stock = new CustomerStock(customerStock, getCustomer(), num, customerStock.getBuyPrice());
        return new SellStock(this,stock).execute();
    }

    public ValuePool<CustomerStock> getStockPool() {
        return stockPool;
    }

    public void setStockPool(ValuePool<CustomerStock> stockPool) {
        this.stockPool = stockPool;
    }

    public ValuePool<StockProfit> getProfitPool() {
        return profitPool;
    }

    public void setProfitPool(ValuePool<StockProfit> profitPool) {
        this.profitPool = profitPool;
    }

    public HashMap<String, Double> getProfit() {
        return profitPool.calTotal(new ValCounter<StockProfit>() {
            @Override
            public double getCountedPrice(StockProfit target) {
                return target.getValue();
            }
        });
    }

    public HashMap<String, Double> getUnrealizedProfit() {
        return stockPool.calTotal(new ValCounter<CustomerStock>() {
            @Override
            public double getCountedPrice(CustomerStock target) {
                return (target.getCurrentPrice()- target.getBuyPrice()) * target.getQuantity();
            }
        });
    }

    public HashMap<String, Double> getTotalValue() {
        HashMap<String, Double> balance = getAmountTotal();
        HashMap<String, Double> stock = getStockAmount();
        HashMap<String,Double> ret = new HashMap<>();
        for (String s : balance.keySet()) {
            ret.put(s, balance.get(s) + stock.get(s));
        }
        return ret;
    }

    public HashMap<String, Double> getStockAmount() {
        return stockPool.calTotal(new ValCounter<CustomerStock>() {
            @Override
            public double getCountedPrice(CustomerStock target) {
                return target.getCurrentPrice() * target.getQuantity();
            }
        });
    }

    public static String createAccountFromAccount(Customer customer){
        if (!customer.hasAccount("Saving")){
            return "You should create a Saving Account and put at least $5000 into the account";
        }else if (customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").removeCurrency(temp);
            SecurityAccount newly = new SecurityAccount(customer);
            customer.addAccount(TYPE, newly);
            AccountDao.getInstance().insertAccount(newly);
            AccountDao.getInstance().updateAccountMoney(customer.getAccount("Saving"),"USD");
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully. ";
        }else{
            return "Your Saving Account should have at least $5000";
        }
    }


    public String transfer(double val, Account account, String currency){
        if (val > getAmount(currency)){
            return "Sorry you only have " + getAmount() + currency + " in your " + getAccountType() + " account";
        }
        account.addCurrency(val);
        this.removeCurrency(val);
        return "Transfer " + val + " from "+ toString() +" account to "+ account.toString()+" account.";
    }

    @Override
    public String transfer(String account,double val, String curr){
        return new Transfer(this, getCustomer().getAccount(account),val , curr).execute();
    }

    @Override
    public boolean canClose() {
        if (stockPool.values().size() > 0){
            return false;
        }

        for (Double value : getAmountTotal().values()) {
            if (value > 0){
                return false;
            }
        }

        return getCustomer().getAccount("Saving").getAmount() >= 10;
    }

    public String transferIn(double val, String curr){
        return new TransferIn(this, val, curr).execute();
    }

    public String transferOut(double val, String curr){
        return new TransferOut(this, val, curr).execute();
    }

}
