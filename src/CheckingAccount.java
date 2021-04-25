import java.io.Serial;

public class CheckingAccount extends Account{

    @Serial
    private static final long serialVersionUID = -5974937777045507260L;
    static int temp = 10;
    public static final String TYPE = "Checking";

    private CheckingAccount(Customer customer, double amount) {
        super(customer, amount, "Checking");
    }

    private CheckingAccount(Customer customer) {
        super(customer, "Checking");
    }

    public static String createAccount(Customer customer){
        // 开户收费？

        // 付钱
        // code

        customer.addAccount(TYPE, new CheckingAccount(customer));
        return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";

    }
}
