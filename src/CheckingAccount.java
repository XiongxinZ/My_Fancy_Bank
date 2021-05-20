import java.io.Serial;

public class CheckingAccount extends Account implements CanDeposit, CanWithdraw, CanExchange, CanTransferWithin, CanTransferToOthers {

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
//        AccountDao.updateCheckingMoney(this, currency);
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
//        AccountDao.updateCheckingMoney(this,currency);
    }

//    public String depositExe(double val, String currency){
//        return new Deposit(this,)
//    }


    public String deposit(double val){
        return deposit(val, "USD");
    }

    public String deposit(double val,String currency){
        return new Deposit(this, val, currency).execute();
    }

    public String withdraw(double val){
        return withdraw(val, "currency");
    }

    public String withdraw(double val,String currency){
        return new Withdraw(this,val,currency).execute();
    }

    public String exchange(String fromType, String toType, double amount){
        return new Exchange(this,amount,fromType,toType).execute();
    }

    @Override
    public String transfer(String account,double val, String curr){
        return new Transfer(this, getCustomer().getAccount(account),val , curr).execute();
//        if (getCustomer().getAccount(account) == null){
//            return "Sorry you don't have the " + account + " account";
//        }
//        return transfer(val, getCustomer().getAccount(account), "USD");
    }

    @Override
    public String transfer(Account account, double val, String curr) {
        return new Transfer(this,account,val,curr).execute();
    }

    @Override
    public String transfer(String email, String accountType, double val, String curr) {
        String c_id = Customer.getId(email);
        return new Transfer(this, c_id, accountType,val,curr).execute();
    }

    public static String createAccountFromCash(Customer customer, double deposit){
        CheckingAccount newly = new CheckingAccount(customer);
        newly.deposit(deposit);
        newly.consume(temp);

        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.getInstance().insertAccount(newly);
        return "Create " + TYPE + " account successfully. Deposit "+deposit +
                "USD, account fee cost "+ConfigUtil.getConfigInt("AccountFee")+
                "USD. Put the remaining "+(deposit - ConfigUtil.getConfigInt("AccountFee"))+"USD into the account. ";
    }

    public static String createAccountFromAccount(Customer customer){
        Account account = customer.getAccount("Saving");
        account.consume(temp);
        CheckingAccount newly = new CheckingAccount(customer);
        customer.addAccount(TYPE, newly);
        customer.markDirty(true);
        AccountDao.getInstance().insertAccount(newly);
        AccountDao.getInstance().updateAccountMoney(account, "USD");
        return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
    }
}
