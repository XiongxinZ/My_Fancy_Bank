public class Exchange extends AbstractTransaction {
    public Exchange(Account from,  double amount, String currencyFrom, String currencyTo) {
        super(from, from, amount, currencyFrom, currencyTo,"Exchange");
    }

    @Override
    public String execute() {
        getFrom().setAmount(getFrom().getAmount(getCurrencyFrom()) - getAmount(), getCurrencyFrom());
        getFrom().setAmount(getFrom().getAmount(getCurrencyTo()) + getAmount() * ConfigUtil.getConfigDouble(getCurrencyFrom()+"To"+getCurrencyTo())*(1-ConfigUtil.getConfigDouble("CheckingRate")), getCurrencyTo());
        AccountDao.getInstance().updateAccountMoney(getFrom(), getCurrencyFrom());
        AccountDao.getInstance().updateAccountMoney(getFrom(),getCurrencyTo());
        TransactionDao.getInstance().insertTransaction(this);
        return "Successful! " + getCurrencyFrom() + getAmount() +" to " + getCurrencyTo() +
                getAmount() * ConfigUtil.getConfigDouble(getCurrencyFrom()+"To"+getCurrencyTo())*(1-ConfigUtil.getConfigDouble("Checking")) +
                "\nFee " + getAmount() * ConfigUtil.getConfigDouble("Checking") + getCurrencyFrom();
    }
}
