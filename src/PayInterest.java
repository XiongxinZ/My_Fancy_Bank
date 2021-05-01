public class PayInterest extends Transaction{
    private double originAmount;
    public PayInterest(String toWhom, double amount, String currency) {
        super(null, toWhom, null, "Saving", amount * ConfigUtil.getConfigInt("SavingRate"), currency);
        originAmount = amount;
    }

    @Override
    public String execute() {
        String ret;
        AccountDao.updateAccountMoney(getToWhom(), getToAccount(), getAmount() + originAmount, getCurrencyTo());
        TransactionDao.insertTransaction(this);
        ret = "Pay Interest " + getAmount() + getCurrencyTo();
        return ret;
    }
}
