import java.io.File;
import java.io.Serial;

public class CollateralValuation implements Order{

    @Serial
    private static final long serialVersionUID = 8331184208579642887L;
    Customer customer;

    String certificateName;
    File file;

    public CollateralValuation(Customer customer, File file) {
        this.customer = customer;
        file = file;
    }

    public CollateralValuation(Customer customer, String filename) {
        this.customer = customer;
        certificateName = filename;
    }

    @Override
    public void execute() {

    }
}
