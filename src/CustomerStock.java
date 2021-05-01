public class CustomerStock {

    private int quantity;
    private Customer customer;
    private String name;
    private String curr;

    private double buyPrice;
    private double currentPrice;

    public CustomerStock(){}

    public CustomerStock(StockInfo stock, Customer customer, int quantity) {
        this.name = stock.getName();
        this.quantity = quantity;
        this.customer = customer;
        this.buyPrice = stock.getPrice();
        this.currentPrice = stock.getPrice();
        this.curr = stock.getCurrency();
    }

    public void merge(CustomerStock stock){
        assert stock.name.equals(name);
        buyPrice = (quantity * buyPrice + stock.buyPrice * stock.quantity)/(quantity + stock.quantity);
        quantity = quantity + stock.quantity;
    }

    public void remove(CustomerStock stock){
        assert stock.name.equals(name);
        buyPrice = (quantity * buyPrice - stock.quantity * stock.currentPrice)/(quantity - stock.quantity);
        quantity = quantity - stock.quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public String getCurr() {
        return curr;
    }

    @Override
    public String toString() {
        return  name +
                ": " + currentPrice + curr + ", Quantity:" + quantity;
    }
}