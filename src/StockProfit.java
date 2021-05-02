public class StockProfit extends StockInfo implements Valuable{
    private double profit;
    private Customer customer;
    public StockProfit(Customer customer,String name, double profit, String currency) {
        super(name, currency);
        this.profit = profit;
        this.customer = customer;
    }

    public double getValue() {
        return profit;
    }

    public Customer getCustomer() {
        return customer;
    }
}
