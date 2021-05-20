public class BuyStock extends StockTransaction{

//    private Stock stock;

    public BuyStock(SecurityAccount account, CustomerStock stock){
        super(account, stock);
    }

    public String execute() {
        if (getAccount().getStockPool().containsKey(getStock().getName())){
            CustomerStock cs = getAccount().getStockPool().get(getStock().getName());
            if (getAccount().getAmount(getStock().getCurrency()) < getStock().getQuantity() * getStock().getBuyPrice()){
                return "You only have enough money";
            }else{
                getAccount().setAmount(
                        getAccount().getAmount(getStock().getCurrency())
                                - getStock().getCurrentPrice() * getStock().getQuantity(),
                        getStock().getCurrency());
                cs.merge(getStock());
                StockDao.updateStockPosition(cs);
                AccountDao.updateAccountMoney(getAccount(), getStock().getCurrency());
                return "Buy "+ getStock().getQuantity() + getStock().getName();
            }
        }else{
            getAccount().setAmount(
                    getAccount().getAmount(getStock().getCurrency())
                            - getStock().getCurrentPrice() * getStock().getQuantity(),
                    getStock().getCurrency());
            getAccount().getStockPool().put(getStock().getName(), getStock());
            StockDao.insertCustomerStock(getStock());
            return "Buy "+ getStock().getQuantity() + getStock().getName();
        }
    }
}