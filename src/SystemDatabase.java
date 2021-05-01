import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

// singleton
public class SystemDatabase {

    private static AtomicReference<SystemDatabase> _instance = new AtomicReference<SystemDatabase>(new SystemDatabase());

    public static final String[] transType = {"Withdraw", "Deposit", "Transfer"};
    public static final String[] accType = {"Saving", "Checking", "Loan", "Security"};

    private HashSet<String> _currencyList;
    private ArrayList<Customer> _customerList;
    private ArrayList<Banker> _bankerList;
    private ArrayList<Order> _orderList;

    private HashMap<String, Serializable> _used;

    private SystemDatabase(){
        _used = new HashMap<>();
    }

}
