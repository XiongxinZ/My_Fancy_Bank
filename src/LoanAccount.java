public class LoanAccount extends Account{

    public static final String TYPE = "Loan";

    static int temp = 10;

    public LoanAccount(Customer customer, double amount) {
        super(customer, amount, "Loan");
    }

    public LoanAccount(Customer customer) {
        super(customer);
    }

    public static String createAccount(Customer customer){
        // only pay from saving account
        if (customer.hasAccount("Saving") && customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").consume(temp);
            customer.addAccount(TYPE, new LoanAccount(customer));
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            return "You can't create Loan Account if you don't have $" + temp + " in your Saving Account";
        }
    }
}
