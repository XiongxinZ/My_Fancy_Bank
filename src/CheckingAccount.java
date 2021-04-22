public class CheckingAccount extends Account{

    static int temp = 10;
    public static final String TYPE = "Checking";

    private CheckingAccount(Customer customer, double amount) {
        super(customer, amount, "Checking");
    }

    private CheckingAccount(Customer customer) {
        super(customer, "Checking");
    }

    public static String createAccount(Customer customer){
        if (customer.hasAccount("Saving") && customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").consume(temp);
            customer.addAccount(TYPE, new CheckingAccount(customer));
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            // 付钱
            // code

            customer.addAccount(TYPE, new CheckingAccount(customer));
            return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";
        }
    }
}
