import java.io.Serial;
import java.io.Serializable;

public class Collateral implements Serializable {
    @Serial
    private static final long serialVersionUID = -1636951387557509685L;
    private double price;

    public Collateral(double price) {
        this.price = price;
    }

//    public static void verifyCollateral(Customer customer){
//
//    }
}
