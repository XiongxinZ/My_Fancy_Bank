public class AutoSolve extends AbstractTransaction {
    public AutoSolve(String fromWhom, String toWhom, String fromAccount, String toAccount, double amount) {
        super(fromWhom, toWhom, fromAccount, toAccount, amount);
    }

    @Override
    public String execute() {
        return null;
    }
}
