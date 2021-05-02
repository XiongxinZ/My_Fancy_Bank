public class CustomerStock extends StockInfo implements Valuable{

    private int quantity;
    private Customer customer;

    private double buyPrice;

    public CustomerStock(StockInfo stock, Customer customer, int quantity, double buyPrice) {
        super(stock.getName(), stock.getCurrentPrice(), stock.getCurrency());
        this.quantity = quantity;
        this.customer = customer;
        this.buyPrice = buyPrice;
    }

    public CustomerStock(StockInfo stock, Customer customer, int quantity) {
        this(stock,customer,quantity,stock.getCurrentPrice());
    }

    public void merge(CustomerStock stock){
        assert stock.getName().equals(getName());
        buyPrice = (quantity * buyPrice + stock.buyPrice * stock.quantity)/(quantity + stock.quantity);
        quantity = quantity + stock.quantity;
    }

    public void remove(CustomerStock stock){
        assert stock.getName().equals(getName());
        buyPrice = (quantity * buyPrice - stock.quantity * stock.getCurrentPrice())/(quantity - stock.quantity);
        quantity = quantity - stock.quantity;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
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



    @Override
    public String toString() {
        return  getName() +
                ": " + getCurrentPrice() + getCurrency() + ", Quantity:" + quantity;
    }

    @Override
    public double getValue() {
        return quantity * getCurrentPrice();
    }
}