import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Stock implements Serializable {

    @Serial
    private static final long serialVersionUid = 2200435301494082440L;
    private String name;
    private int quantity;
    private Customer customer;

    public Stock(){ }

    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Stock(Customer customer, String name, int quantity) {
        this(name,quantity);
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        if (stock.customer == null){
            return Objects.equals(name, stock.name);
        }else{
            return Objects.equals(name, stock.name) && customer.getId().equals(stock.customer.getId());
        }
    }

    @Override
    public int hashCode() {
        if (customer != null){
            return Objects.hash(name, customer.getId());
        }else{
            return Objects.hash(name);
        }
    }
}