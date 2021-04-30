public class Transfer extends Transaction{
    public Transfer(Account from, Account to, double amount, String currency) {
        super(from, to, amount, currency);
    }

    @Override
    public String execute() {
        String ret;
        if (getAmount() > getFrom().getAmount(getCurrencyFrom())){
            ret =  "Sorry you only have $" + getFrom().getAmount("USD") + " in your " + getFrom().getAccountType() + "account";
        }else{
            getFrom().removeCurrency(getAmount(), getCurrencyFrom());
            getTo().addCurrency(getAmount(), getCurrencyTo());
            ret = "Transfer " + getAmount() + " from "+ getFrom().toString() +" to "+ getTo().toString();
            TransactionDao.insertTransaction(this);
        }
        return ret;
    }
}
