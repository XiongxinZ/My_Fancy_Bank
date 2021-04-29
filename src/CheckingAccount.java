import java.io.Serial;

public class CheckingAccount extends Account implements CanDeposit, CanWithdraw{

    @Serial
    private static final long serialVersionUid = -5974937777045507260L;
    static int temp = 10;
    public static final String TYPE = "Checking";

    public CheckingAccount(Customer customer, double amount) {
        super(customer, amount, "Checking");
    }

    public CheckingAccount(Customer customer) {
        super(customer, "Checking");
    }

    @Override
    protected void addCurrency(double val, String currency) {
        super.addCurrency(val, currency);
        AccountDao.updateCheckingMoney(this, currency);
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
        AccountDao.updateCheckingMoney(this,currency);
    }


    public String deposit(double val){
        return deposit(val, "USD");
    }

    public String deposit(double val,String currency){
        addCurrency(val, currency);
        return "Deposit $" + val + ", amount $" + getAmount();
    }

    public String withdraw(double val){
        return withdraw(val, "currency");
    }

    public String withdraw(double val,String currency){
        removeCurrency(val, currency);
        return "Withdraw $" + val + ", amount $" + getAmount();
    }

    public static String createAccountFromCash(Customer customer, double deposit){
        CheckingAccount newly = new CheckingAccount(customer);
        newly.deposit(deposit);
        newly.consume(temp);

        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.insertChecking(newly);
        return "Create " + TYPE + " account successfully. Deposit "+deposit +
                ", account fee cost "+ConfigUtil.getConfigInt("AccountFee")+
                ". Put the remaining "+(deposit - ConfigUtil.getConfigInt("AccountFee"))+"into the account. ";
    }

    public static String createAccountFromAccount(Customer customer){
        Account account = customer.getAccount("Saving");
        account.consume(temp);
        CheckingAccount newly = new CheckingAccount(customer);
        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.insertChecking(newly);
        return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";
    }
}
