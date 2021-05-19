import java.util.Date;

public class PayInterest extends AbstractTransaction {
    private double originAmount;
    public PayInterest(String toWhom, double amount, String currency, Date date) {
        super(null, toWhom, null, "Saving",
                amount * ConfigUtil.getConfigDouble("SavingRate")/365.0, currency,"Pay Interest");
        setTransTime(date);
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

    @Override
    public Double getToBalance() {
        return getAmount() + originAmount;
    }
}
