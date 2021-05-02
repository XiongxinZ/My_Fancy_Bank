public class StockInfo {

    private String name;
    private double currentPrice;
    private String currency;

    public StockInfo(String name, double currentPrice, String currency) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.currency = currency;
    }

    public StockInfo(String name, String currency) {
        this.name = name;
        this.currency = currency;
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

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return name + ": " + currentPrice + currency;
    }
}
