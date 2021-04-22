import java.io.Serial;

public class TakeLoan implements Order{

    @Serial
    private static final long serialVersionUID = -6531033077785053223L;
    private Customer customer;
    private Collateral collateral;
    public TakeLoan(Customer customer, Collateral collateral) {
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
