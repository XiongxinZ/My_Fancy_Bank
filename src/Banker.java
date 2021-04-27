import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Banker extends User{
    @Serial
    private static final long serialVersionUid = 1951449345466306912L;
    private List<Order> orderList = SystemDatabase.getOrderList();

    public Banker(String name, String pwd, String email) {
        super(name, pwd, "Banker", email);
    }

    public void takeOrder(Order order){
        orderList.add(order);
    }

    public void placeOrders(){
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
