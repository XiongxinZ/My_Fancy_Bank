public class Exchange extends Transaction{
    public Exchange(Account from,  double amount, String currencyFrom, String currencyTo) {
        super(from, from, amount, currencyFrom, currencyTo);
    }

    @Override
    public String execute() {
        getFrom().setAmount(getFrom().getAmount(getCurrencyFrom()) - getAmount(), getCurrencyFrom());
        getFrom().setAmount(getFrom().getAmount(getCurrencyTo()) + getAmount() * ConfigUtil.getConfigDouble(getCurrencyFrom()+"To"+getCurrencyTo()), getCurrencyTo());
        AccountDao.updateAccountMoney(getFrom(), getCurrencyFrom());
        AccountDao.updateAccountMoney(getFrom(),getCurrencyTo());
        TransactionDao.insertTransaction(this);
        return "Successful! " + getCurrencyFrom() + getAmount() +" to " + getCurrencyTo() + getAmount() * ConfigUtil.getConfigDouble(getCurrencyFrom()+"To"+getCurrencyTo());
    }
}
