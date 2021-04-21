import java.util.ArrayList;
import java.util.List;

public class Banker extends User{
    private List<Order> orderList = new ArrayList<Order>();

    public Banker(String name) {
        super(name);
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
