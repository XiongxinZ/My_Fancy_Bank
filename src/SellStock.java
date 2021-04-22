import java.io.Serial;

public class SellStock implements Order {
    @Serial
    private static final long serialVersionUID = -1097931013329809778L;
    private Stock stock;

    public SellStock(Stock stock){
        this.stock = stock;
    }

    public void execute() {
        stock.sell();
    }
}
