public class Transfer extends Transaction{
    public Transfer(Account from, Account to, double amount) {
        super(from, to, amount);
    }

    @Override
    public String execute() {
        String ret;
        if (getAmount() > getFrom().getAmount("USD")){
            ret =  "Sorry you only have $" + getFrom().getAmount("USD") + " in your " + getFrom().getAccountType() + "account";
        }else{
            ret = getFrom().transfer(getTo(), getAmount());
            TransactionDao.insertTransaction(this);
        }
        return ret;
    }
}
