
public class SellStock extends StockTransaction{

    public SellStock(SecurityAccount account, CustomerStock stock){
        super(account, stock);
    }

    public String execute() {
//        stock.sell();
        if (getAccount().getStockPool().containsKey(getStock().getName())){
            CustomerStock cs = getAccount().getStockPool().get(getStock().getName());
            if (cs.getQuantity() < getStock().getQuantity()){
                return "You only have " + cs.getQuantity() +" " + cs.getName();
            }else{
                getAccount().setAmount(
                        getAccount().getAmount(getStock().getCurrency())
                                + getStock().getQuantity() * getStock().getCurrentPrice(),
                        getStock().getCurrency());
                cs.remove(getStock());
                if (cs.getQuantity() == 0){
                    StockProfit profit = new StockProfit(getStock());
                    if (getAccount().getProfitPool().containsKey(profit.getName())){
                        StockProfit val =getAccount().getProfitPool().get(profit.getName());
                        val.setProfit(val.getProfit() + profit.getProfit());
                        StockDao.getInstance().updateProfit(val);
                    }else{
                        getAccount().getProfitPool().put(profit.getName(), profit);
                        StockDao.getInstance().insertProfit(profit);
                    }
                    getAccount().getStockPool().remove(cs.getName());
                    StockDao.getInstance().removeCustomerStock(cs);
                }else{
                    StockProfit profit = new StockProfit(getStock());
                    if (getAccount().getProfitPool().containsKey(profit.getName())){
                        StockProfit val =getAccount().getProfitPool().get(profit.getName());
                        val.setProfit(val.getProfit() + profit.getProfit());
                        StockDao.getInstance().updateProfit(val);
                    }else{
                        getAccount().getProfitPool().put(profit.getName(), profit);
                        StockDao.getInstance().insertProfit(profit);
                    }
                    StockDao.getInstance().updateStockPosition(cs);
                }
                AccountDao.getInstance().updateAccountMoney(getAccount(), getStock().getCurrency());
                return "Sold "+ getStock().getQuantity() + getStock().getName();
            }
        }else{
            return "You don't have " + getStock().getName();
        }
    }
}
