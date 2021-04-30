public class StringUtil {

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
