import java.io.Serial;

public class SavingAccount extends Account implements CanDeposit, CanTransferWithin {

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
        return deposit(val,"USD");
    }

    public String deposit(double val,String currency){
        addCurrency(val, currency);
        AccountDao.getInstance().updateAccountMoney(this,currency);
        return "Deposit $" + val + ", amount $" + getAmount();
    }

    @Override
    public String transfer(String account,double val, String curr){
        return new Transfer(this, getCustomer().getAccount(account),val , curr).execute();
    }

//    @Override
//    public String transfer(Account account, double val, String curr) {
//        return new Transfer(this,account,val,curr).execute();
//    }

    public static String createAccountFromCash(Customer customer, double deposit){
        SavingAccount newly = new SavingAccount(customer);
        newly.deposit(deposit);
        newly.consume(ConfigUtil.getConfigInt("AccountFee"));

        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.getInstance().insertAccount(newly);
        return "Create " + TYPE + " account successfully. Deposit "+deposit +
                "USD, account fee cost "+ConfigUtil.getConfigInt("AccountFee")+
                "USD. Put the remaining "+(deposit - ConfigUtil.getConfigInt("AccountFee"))+"USD nto the account. ";    }

    public static String createAccountFromAccount(Customer customer){
        customer.getAccount("Checking").removeCurrency(ConfigUtil.getConfigInt("AccountFee"));
        SavingAccount newly = new SavingAccount(customer);
        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.getInstance().insertAccount(newly);
        AccountDao.getInstance().updateAccountMoney((CheckingAccount) customer.getAccount("Checking"), "USD");
        return "Pay the fee from Checking Account automatically. Create " + TYPE + " account successfully";
    }

}
