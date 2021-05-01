
public class SellStock {
    private Stock stock;

    public SellStock(Account account, Stock stock){
//        super();
        this.stock = stock;
    }

    public String execute() {
        stock.sell();
        return null;
    }
}
