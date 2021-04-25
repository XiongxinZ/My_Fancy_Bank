import java.io.Serial;
import java.util.HashMap;

public class Customer extends User{
    @Serial
    private static final long serialVersionUID = 6699128377666927421L;
    private HashMap<String, Account> accountList = new HashMap<>();
    public Customer(String name, String pwd) {
        super(name, pwd, "Customer");
//        setId(getId() * 31 + "Customer".hashCode());
    }

    public Customer() {}

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

    public String deposit(double val, String currency, String accountType){
        return new Deposit(accountList.get(accountType), val, currency).execute();
    }

    public String deposit(double val, String currency){
        return deposit(val, currency, "Saving");
    }

    public String deposit(double val){
        return deposit(val, "USD");
    }

    public String withdraw(double val, String currency, String accountType){
        return new Withdraw(accountList.get(accountType), val, currency).execute();
    }

    public String withdraw(double val, String currency){
        return withdraw(val, currency, "Checking");
    }

    public String withdraw(double val){
        return withdraw(val, "USD");
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
