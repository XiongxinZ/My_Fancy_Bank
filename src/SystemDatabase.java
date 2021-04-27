import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

// singleton
public class SystemDatabase {

    private static AtomicReference<SystemDatabase> _instance = new AtomicReference<SystemDatabase>(new SystemDatabase());

    public static final String[] transType = {"Withdraw", "Deposit", "Transfer"};

    private HashSet<String> _currencyList;
    private ArrayList<Customer> _customerList;
    private ArrayList<Banker> _bankerList;
    private ArrayList<Order> _orderList;

    private HashMap<String, Serializable> _used;

    private SystemDatabase(){
        _used = new HashMap<>();
    }

    public static HashSet<String> getCurrencyList() {
        String filename = "currency";
        if (_instance.get()._currencyList == null){
            _instance.get()._currencyList =  (HashSet<String>) FileUtil.getData(filename + ".txt");
            if (_instance.get()._currencyList == null){
                _instance.get()._currencyList = new HashSet<>();
            }
            _instance.get()._used.put(filename,_instance.get()._currencyList);
        }

        return _instance.get()._currencyList;
    }

    public static List<Customer> getCustomerList() {
        String filename = "customer";
        if (_instance.get()._customerList == null){
            _instance.get()._customerList =  (ArrayList<Customer>) FileUtil.getData(filename + ".txt");
            if (_instance.get()._customerList == null){
                _instance.get()._customerList = new ArrayList<>();
            }
            _instance.get()._used.put(filename,_instance.get()._customerList);
        }
        return _instance.get()._customerList;
    }

    public static List<Banker> getBankerList() {
        String filename = "banker";
        if (_instance.get()._bankerList == null){
            _instance.get()._bankerList =  (ArrayList<Banker>) FileUtil.getData(filename + ".txt");
            if (_instance.get()._bankerList == null){
                _instance.get()._bankerList = new ArrayList<>();
            }
            _instance.get()._used.put(filename,_instance.get()._bankerList);
        }
        return _instance.get()._bankerList;
    }

    public static List<Order> getOrderList() {
        String filename = "order";
        if (_instance.get()._orderList == null){
            _instance.get()._orderList =  (ArrayList<Order>) FileUtil.getData(filename + ".txt");
            if (_instance.get()._orderList == null){
                _instance.get()._orderList = new ArrayList<>();
            }
            _instance.get()._used.put(filename,_instance.get()._orderList);
        }
        return _instance.get()._orderList;
    }

    public static void writeAllDirtyData(){
        HashMap<String, Serializable> used = _instance.get()._used;
        for (Map.Entry<String, Serializable> data : used.entrySet()) {
            FileUtil.writeData(data.getKey() + ".txt", data.getValue());
        }
    }

    public static void main(String[] args) {
        getCustomerList();
    }

}
