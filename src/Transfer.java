public class Transfer extends Transaction{
    public Transfer(Account from, Account to, double amount, String currency) {
        super(from, to, amount, currency);
    }
    public Transfer(Account from, String to,String toAccount, double amount, String currency) {
        super(from, to,toAccount, amount, currency);
    }

    @Override
    public String execute() {
        String ret;
        if (getAmount() > getFrom().getAmount(getCurrencyFrom())){
            ret =  "Sorry you only have $" + getFrom().getAmount("USD") + " in your " + getFrom().getAccountType() + "account";
        }else if (getTo() == null){
            getFrom().removeCurrency(getAmount(), getCurrencyFrom());
            AccountDao.updateAccountMoney(getToWhom(), getToAccount(), getAmount(), getCurrencyTo());
            ret = "Transfer success";
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
