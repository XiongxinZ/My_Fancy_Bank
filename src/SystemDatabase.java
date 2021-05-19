import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

// singleton
public class SystemDatabase {

    private static AtomicReference<SystemDatabase> _instance = new AtomicReference<SystemDatabase>(new SystemDatabase());

    public static final String[] currType = {"USD", "CNY", "JPY"};
    public static final String[] transType = {"Withdraw", "Deposit", "Transfer","Transfer In","Transfer Out","Take Loan"};
    public static final String[] accType = {"Saving", "Checking", "Loan", "Security"};
    public static final String[] month = {"All","Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.", "Jul.",
            "Aug.","Sept.","Oct.","Nov.","Dec."};
    public static final String[] day = new String[32];
    public static final HashMap<String, Integer> monthMap = new HashMap<>();

    static {
        day[0] = "All";
        for (int i = 1; i < day.length; i++) {
            day[i] = String.valueOf(i);
        }

        for (int i = 1; i < month.length; i++) {
            monthMap.put(month[i], i-1);
        }
    }

    private HashMap<String, Serializable> _used;

    private SystemDatabase(){
        _used = new HashMap<>();
    }

}
