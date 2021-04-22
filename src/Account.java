import java.io.Serializable;
import java.util.Currency;

public class Account implements Serializable {
    private double amount = 0.0;
    private Customer customer;
    private String accountType;

    public Account(Customer customer, double amount, String accountType) {
        this.amount = amount;
        this.customer = customer;
        this.accountType = accountType;
    }

    public Account(Customer customer, String accountType) {
        this(customer, 0.0, accountType);
    }

    public Account(Customer customer, double amount) {
        this(customer, amount, "Saving");
    }

    public Account(Customer customer) {
        this(customer, 0.0, "Saving");
    }

    // Transfer to another account
    public void transfer(Account account, double val){
        System.out.printf("Transfer %.2f from %s account to %s account.\n", val, toString(), account.toString());
    }

    // Transfer to customer's another account
    public void transfer(String account, double val){

        System.out.printf("Transfer %.2f from %s account to %s account.\n", val, toString(), account);
    }

    public void consume(double val){
        assert  amount >= val;
        amount -= val;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return customer.getName() + "'s " + accountType + " Account";
    }
}
