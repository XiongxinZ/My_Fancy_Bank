import java.util.Date;

public abstract class StockTransaction implements Transaction{
    private SecurityAccount account;
    private CustomerStock stock;
    private String type;
    private double price;
    private Date date;
    public StockTransaction(SecurityAccount account, CustomerStock stock, String type, Date date) {
        this.stock = stock;
        this.account = account;
        this.type = type;
        this.price = stock.getCurrentPrice();
        this.date = date;
    }

    public StockTransaction(SecurityAccount account, CustomerStock stock, String type) {
        this.stock = stock;
        this.account = account;
        this.type = type;
        this.price = stock.getCurrentPrice();
        this.date = new Date();
    }

    public abstract String execute();

    public SecurityAccount getAccount() {
        return account;
    }

    public CustomerStock getStock() {
        return stock;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
