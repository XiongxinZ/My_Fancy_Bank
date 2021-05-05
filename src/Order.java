import java.io.Serializable;

/**
 * This interface serves as a contract to the user that if a class implements it, the class should be able to process orders.
 *
 */
public interface Order extends Transaction{
   String apply();
   String execute();
}