public class SecurityAccount extends Account{

    public static final String TYPE = "Security";

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
