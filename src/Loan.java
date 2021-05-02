import java.util.Date;
import java.util.Objects;

public class Loan implements Valuable{
    private double balance;
    private Collateral collateral;
    private Customer customer;
    private String currency;
    private String id;

    public Loan(Customer customer, Collateral collateral,double balance,String currency, String id) {
        this.balance = balance;
        this.collateral = collateral;
        this.customer = customer;
        this.currency = currency;
        this.id = id;
    }


    public Loan(Customer customer, Collateral collateral,double balance, String currency) {
        this(customer,collateral,balance,currency,
                String.valueOf(Long.parseLong(collateral.getId()) * 31L + new Date().hashCode()));
    }

    public String repayment(double amount){
        return new Repayment(this,customer.getAccount("Loan"), amount, currency).execute();
    }

    public double getBalance() {
        return balance;
    }

    public Collateral getCollateral() {
        return collateral;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public void repay(double amount){
        this.balance = this.balance - amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public double getValue() {
        return balance;
    }

    @Override
    public String toString() {
        return collateral.getName()+
                ", balance:" + balance+currency;
    }
}
