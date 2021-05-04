// store realized profit the customer gain from the stock
public class StockProfit extends StockInfo implements Valuable{
    private double profit;
    private Customer customer;
    public StockProfit(Customer customer,String name, double profit, String currency) {
        super(name, currency);
        this.profit = profit;
        this.customer = customer;
    }

    public StockProfit(CustomerStock customerStock) {
        super(customerStock.getName(), customerStock.getCurrency());
        this.profit = (customerStock.getCurrentPrice() - customerStock.getBuyPrice()) * customerStock.getQuantity();
        this.customer = customerStock.getCustomer();
    }

    public double getValue() {
        return profit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfit() {
        return profit;
    }
}
