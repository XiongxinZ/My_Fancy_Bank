import java.io.Serial;

public class SavingAccount extends Account implements CanDeposit, CanTransfer{

    public static final String TYPE = "Saving";
    @Serial
    private static final long serialVersionUid = -2481826816986733553L;

    public SavingAccount(Customer customer, double amount) {
        super(customer, amount, "Saving");
    }

    public SavingAccount(Customer customer) {
        super(customer, "Saving");
    }

    public String deposit(double val){
        addCurrency(val);
        return "Deposit $" + val + ", amount $" + getAmount();
    }

    public String deposit(double val,String currency){
        addCurrency(val, currency);
        return "Deposit $" + val + ", amount $" + getAmount();
    }

    // Transfer to customer's another account

    public String transfer(double val, Account account, String currency){
        if (val > getAmount(currency)){
            return "Sorry you only have " + getAmount() + currency + " in your " + getAccountType() + "account";
        }
        account.addCurrency(val);
        this.removeCurrency(val);
        return "Transfer " + val + " from "+ toString() +" account to "+ account.toString()+"account.";
    }

    // Transfer to another account
    public String transfer(double val,Account account){
        return transfer(val, account, "USD");
    }

    public String transfer(double val,String account){
        if (getCustomer().getAccount(account) == null){
            return "Sorry you don't have the " + account + " account";
        }
        return transfer(val, getCustomer().getAccount(account), "USD");
    }

    @Override
    protected void addCurrency(double val, String currency) {
        super.addCurrency(val, currency);
        AccountDao.updateSavingMoney(this, currency);
    }

    @Override
    protected void addCurrency(double val) {
        addCurrency(val, "USD");
    }

    @Override
    protected void removeCurrency(double val) {
        removeCurrency(val,"USD");
    }

    @Override
    protected void removeCurrency(double val, String currency) {
        super.removeCurrency(val, currency);
        AccountDao.updateSavingMoney(this,currency);
    }

    public static String createAccountFromCash(Customer customer, double deposit){
        SavingAccount newly = new SavingAccount(customer);
        newly.deposit(deposit);
        newly.consume(ConfigUtil.getConfigInt("AccountFee"));

        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.insertSaving(newly);
        return "Create " + TYPE + " account successfully. Deposit "+deposit +
                ", account fee cost "+ConfigUtil.getConfigInt("AccountFee")+
                ". Put the remaining "+(deposit - ConfigUtil.getConfigInt("AccountFee"))+"into the account. ";    }

    public static String createAccountFromAccount(Customer customer){
        customer.getAccount("Checking").removeCurrency(ConfigUtil.getConfigInt("AccountFee"));
        SavingAccount newly = new SavingAccount(customer);
        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.insertSaving(newly);
        return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
    }

}
