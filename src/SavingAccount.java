import java.io.Serial;

public class SavingAccount extends Account implements CanDeposit, CanTransfer{

    public static final String TYPE = "Saving";
    @Serial
    private static final long serialVersionUid = -2481826816986733553L;

    private SavingAccount(Customer customer, double amount) {
        super(customer, amount, "Saving");
    }

    private SavingAccount(Customer customer) {
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

    public static String createAccount(Customer customer){

        if (customer.hasAccount("Checking") && customer.getAccount("Checking").getAmount() > 10){
            customer.getAccount("Checking").removeCurrency(10);
            customer.addAccount(TYPE, new SavingAccount(customer));
            customer.markDirty(true);
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            // 付钱
            // code
            customer.markDirty(true);
            customer.addAccount(TYPE, new SavingAccount(customer));
            return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";
        }
    }
}
