import java.io.Serial;

public class BuyStock implements Order {
    @Serial
    private static final long serialVersionUid = -7890351294592708475L;
    private Stock stock;

    public BuyStock(Stock stock){
        this.stock = stock;
    }

    public void execute() {
        stock.buy();
    }
}