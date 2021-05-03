public class TransferIn extends Transfer {
    public TransferIn(Account to, double amount, String currency) {
        super(to.getCustomer().getAccount("Saving"), to, amount, currency,"Transfer In");
    }

    @Override
    public String execute() {
        String ret;
        double fromBar = ConfigUtil.getConfigInt("TransferInSavingBar") * ConfigUtil.getConfigDouble("USDTo"+getCurrencyTo());
        double fromRemainingBar = ConfigUtil.getConfigInt("TransferInRemainingBar") * ConfigUtil.getConfigDouble("USDTo"+getCurrencyTo());
        double amountBar = ConfigUtil.getConfigInt("TransferInBar") * ConfigUtil.getConfigDouble("USDTo"+getCurrencyTo());

        if (getFrom().getAmount(getCurrencyFrom()) < fromBar){
            ret = "Your saving account should have at least " + fromBar + getCurrencyFrom();
        }else if(getFrom().getAmount(getCurrencyFrom()) - getAmount() < fromRemainingBar){
            ret = "Your saving account should remain at least " + fromRemainingBar + getCurrencyFrom();
        }else if (getAmount() < amountBar){
            ret = "Your should transfer in at least " + amountBar + getCurrencyFrom();
        }else if (getAmount() > getFrom().getAmount(getCurrencyFrom())){
            ret =  "Sorry you only have $" + getFrom().getAmount("USD") + " in your " + getFrom().getAccountType() + "account";
        }else{
            getFrom().removeCurrency(getAmount(), getCurrencyFrom());
            getTo().addCurrency(getAmount(), getCurrencyTo());
            ret = "Transfer " + getAmount() + " from "+ getFrom().toString() +" to "+ getTo().toString();
            TransactionDao.insertTransaction(this);
            AccountDao.updateAccountMoney(getTo(),getCurrencyTo());
            AccountDao.updateAccountMoney(getFrom(),getCurrencyFrom());
        }
        return ret;
    }
}
