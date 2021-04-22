import java.util.HashMap;

public class Customer extends User{
    private HashMap<String, Account> accountList = new HashMap<>();
    public Customer(String name, String pwd) {
        super(name, pwd);
    }

    public String createAccountAndReturnMessage(String accountType){
        if (accountList.containsKey(accountType)){
            return "You already have the " + accountType +"Account.";
        }

        return switch (accountType) {
            case "Saving" -> SavingAccount.createAccount(this);
            case "Checking" -> CheckingAccount.createAccount(this);
            case "Loan" -> LoanAccount.createAccount(this);
            case "Security" -> SecurityAccount.createAccount(this);
            default -> null;
        };
    }

    public String deposit(double val){
        return ((SavingAccount) accountList.get("Saving")).deposit(val);
    }

    public boolean hasAccount(String accountType){
        return accountList.containsKey(accountType);
    }

    public boolean hasAccounts(){
        return accountList.size() != 0;
    }

    public Account getAccount(String accountType){
        return accountList.get(accountType);
    }

    public void addAccount(String accountType, Account account){
        accountList.put(accountType, account);
    }

}
