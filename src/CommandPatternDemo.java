public class CommandPatternDemo {
    public static void main(String[] args) {
        Stock stock = new Stock();

        BuyStock buyStockOrder = new BuyStock(stock);
        SellStock sellStockOrder = new SellStock(stock);

        Banker banker = new Banker("CPK", "123");
        banker.takeOrder(buyStockOrder);
        banker.takeOrder(sellStockOrder);

        banker.placeOrders();
    }
}