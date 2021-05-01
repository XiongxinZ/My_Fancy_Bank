public class BuyStock extends StockTransaction{

//    private Stock stock;

    public BuyStock(SecurityAccount account, CustomerStock stock){
        super(account, stock);
    }

    public String execute() {
        if (getAccount().getPool().containsKey(getStock().getName())){
            CustomerStock cs = getAccount().getPool().get(getStock().getName());
            if (getAccount().getAmount(getStock().getCurr()) < getStock().getQuantity() * getStock().getBuyPrice()){
                return "You only have enough money";
            }else{
                getAccount().setAmount(
                        getAccount().getAmount(getStock().getCurr())
                                - getStock().getCurrentPrice() * getStock().getQuantity(),
                        getStock().getCurr());
                cs.merge(getStock());
                StockDao.updateStockPosition(cs);
                return "Buy "+ getStock().getQuantity() + getStock().getName();
            }
        }else{
            getAccount().getPool().put(getStock().getName(), getStock());
            StockDao.insertCustomerStock(getStock());
            return "Buy "+ getStock().getQuantity() + getStock().getName();
        }
    }
}