import java.io.Serial;

public class SecurityAccount extends Account{

    public static final String TYPE = "Security";
    @Serial
    private static final long serialVersionUID = 637490753922657801L;

    static int temp = 5000;

    public SecurityAccount(Customer customer, double amount) {
        super(customer, amount);
    }

    public SecurityAccount(Customer customer) {
        super(customer);
    }

    public static String createAccount(Customer customer){
        if (!customer.hasAccount("Saving")){
            return "You should create a Saving Account and put at least $5000 into the account";
        }else if (customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").consume(temp);
            customer.addAccount(TYPE, new LoanAccount(customer));
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully. ";
        }else{
            return "Your Saving Account should have at least $5000";
        }
    }
}
