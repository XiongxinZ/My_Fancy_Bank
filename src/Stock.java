import java.io.Serial;
import java.io.Serializable;

public class Stock implements Serializable {

    @Serial
    private static final long serialVersionUid = 2200435301494082440L;
    private String name;
    private int quantity = 10;

    public Stock(){ }

    public Stock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void buy(){
        System.out.println("Stock [ Name: "+name+", Quantity: " + quantity +" ] bought");
    }

    public void sell(){
        System.out.println("Stock [ Name: "+name+", Quantity: " + quantity +" ] sold");
    }
}