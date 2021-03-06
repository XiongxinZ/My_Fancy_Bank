
public class SavingAccount extends Account implements CanDeposit, CanTransferWithin {

    public static final String TYPE = "Saving";

    public SavingAccount(Customer customer, double amount) {
        super(customer, amount, "Saving");
    }

    public SavingAccount(Customer customer) {
        super(customer, "Saving");
    }

    public String deposit(double val){
        return deposit(val, "USD");
    }

    public String deposit(double val,String currency){
        return new Deposit(this, val, currency).execute();
    }

    @Override
    public String transfer(String account,double val, String curr){
        return new Transfer(this, getCustomer().getAccount(account),val , curr).execute();
    }

    @Override
    public boolean canClose() {
        if (getCustomer().hasAccount("Security")){
            return false;
        } else if (getCustomer().hasAccount("Loan")){
            return false;
        } else if (!getCustomer().hasAccount("Checking")){
            return false;
        } else{
            for (Double value : getAmountTotal().values()) {
                if (value > 0){
                    return false;
                }
            }
        }
        return getCustomer().getAccount("Checking").getAmount() >= 10;
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
        AccountDao.getInstance().insertAccount(newly);
        return "Create " + TYPE + " account successfully. Deposit "+deposit +
                "USD, account fee cost "+"<font color=\"red\">"+ConfigUtil.getConfigInt("AccountFee")+"</font>"+
                "USD. Put the remaining "+"<font color=\"red\">"+(deposit - ConfigUtil.getConfigInt("AccountFee"))+"</font>"+"USD nto the account. ";    }

    public static String createAccountFromAccount(Customer customer){
        customer.getAccount("Checking").removeCurrency(ConfigUtil.getConfigInt("AccountFee"));
        SavingAccount newly = new SavingAccount(customer);
        customer.addAccount(TYPE, newly);
        AccountDao.getInstance().insertAccount(newly);
        AccountDao.getInstance().updateAccountMoney((CheckingAccount) customer.getAccount("Checking"), "USD");
        return "Pay the fee from Checking Account automatically. Create " + TYPE + " account successfully";
    }

}
