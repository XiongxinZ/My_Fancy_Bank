public class SavingAccount extends Account{

    public static final String TYPE = "Saving";

    private SavingAccount(Customer customer, double amount) {
        super(customer, amount, "Saving");
    }

    private SavingAccount(Customer customer) {
        super(customer, "Saving");
    }


    public static String createAccount(Customer customer){
        // 开户收费？

        // 付钱
        // code

        customer.addAccount(TYPE, new SavingAccount(customer));
        return "Create " + TYPE + " account successfully. Deposit %.2f, account fee cost %d. Put the remaining %.2f into the account. ";

    }
}
