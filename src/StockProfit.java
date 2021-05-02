public class StockProfit extends StockInfo {
    private double profit;
    private Customer customer;
    public StockProfit(Customer customer,String name, double profit, String currency) {
        super(name, currency);
        this.profit = profit;
        this.customer = customer;
    }

    public double getProfit() {
        return profit;
    }

    public Customer getCustomer() {
        return customer;
    }
}
