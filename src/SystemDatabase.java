import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

// singleton
public class SystemDatabase {

    private static AtomicReference<SystemDatabase> _instance = new AtomicReference<SystemDatabase>(new SystemDatabase());
    private final HashSet<String> _currencyList = new HashSet<>();


    private SystemDatabase(){
//        _currencyList =
    }

    public static HashSet<String> getCurrencyList() {
        return _instance.get()._currencyList;
    }

}
