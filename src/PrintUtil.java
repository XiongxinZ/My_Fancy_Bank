import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PrintUtil {
    private static NumberFormat myFormatter = NumberFormat.getInstance();

    static {
        myFormatter.setMinimumFractionDigits(2);
        myFormatter.setMaximumFractionDigits(2);
    }
    public static String printDouble(double val){
        return myFormatter.format(val);
    }

    public static String getHomepageButtonString(Customer customer, String type){
        if (customer.hasAccount(type)){
            return getAccountInfoString(customer.getAccount(type));
        }else{
            return "<html>Click to create <b>" + type + "</b> account";
        }
    }

    public static String getAccountInfoString(Account account){
//        String type = account.getAccountType();
        return "<html><b>" + account.getAccountType() + "Account:</b><br>" +
                "USD: " + account.getAmount("USD") + "<br>" +
                "CNY: " + account.getAmount("CNY") + "<br>" +
                "JPY: " + account.getAmount("JPY") + "<br>";

    }
}
