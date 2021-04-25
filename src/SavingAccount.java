import java.io.Serial;

public class SavingAccount extends Account{

    public static final String TYPE = "Saving";
    @Serial
    private static final long serialVersionUID = -2481826816986733553L;

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

    public static String createAccount(Customer customer){

        if (customer.hasAccount("Checking") && customer.getAccount("Checking").getAmount() > 10){
            customer.getAccount("Checking").consume(10);
            customer.addAccount(TYPE, new SavingAccount(customer));
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            // 付钱
            // code

            customer.addAccount(TYPE, new SavingAccount(customer));
            return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";
        }
    }
}
