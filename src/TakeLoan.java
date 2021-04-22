public class TakeLoan implements Order{

    private Customer customer;
    private Object collateral;
    public TakeLoan(Customer customer, Object collateral) {
        this.customer = customer;
        this.collateral = collateral;
    }

    public TakeLoan(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void execute() {

    }
}
