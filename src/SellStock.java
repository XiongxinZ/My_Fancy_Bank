
public class SellStock extends StockTransaction{

    public SellStock(SecurityAccount account, CustomerStock stock){
        super(account, stock);
    }

    public String execute() {
//        stock.sell();
        if (getAccount().getPool().containsKey(getStock().getName())){
            CustomerStock cs = getAccount().getPool().get(getStock().getName());
            if (cs.getQuantity() < getStock().getQuantity()){
                return "You only have " + cs.getQuantity() +" " + cs.getName();
            }else{
                getAccount().setAmount(
                        getAccount().getAmount(getStock().getCurr())
                                + getStock().getQuantity() * getStock().getCurrentPrice(),
                        getStock().getCurr());
                cs.remove(getStock());
                if (cs.getQuantity() == 0){
                    getAccount().getPool().remove(cs.getName());
                    StockDao.removeCustomerStock(cs);
                }else{
                    StockDao.updateStockPosition(cs);
                }
                AccountDao.updateAccountMoney(getAccount(), getStock().getCurr());
                return "Sold "+ getStock().getQuantity() + getStock().getName();
            }
        }else{
            return "You don't have " + getStock().getName();
        }
    }
}
