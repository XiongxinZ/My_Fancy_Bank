public class ComboList {

    public static String[] getTransactionTypeList(){
        return SystemDatabase.transType;
    }

    public static String[] getAccountTypeList(){
        return SystemDatabase.accType;
    }
}
