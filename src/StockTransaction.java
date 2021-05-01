public abstract class StockTransaction implements Transaction{
    private SecurityAccount account;
    private CustomerStock stock;
    public StockTransaction(SecurityAccount account, CustomerStock stock) {
        this.stock = stock;
        this.account = account;
    }

    public abstract String execute();

    public SecurityAccount getAccount() {
        return account;
    }

    public CustomerStock getStock() {
        return stock;
    }
}
