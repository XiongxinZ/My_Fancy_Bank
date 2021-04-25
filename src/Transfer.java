public class Transfer extends Transaction{
    public Transfer(Account from, Account to, double amount) {
        super(from, to, amount);
    }

    @Override
    public String execute() {
        return getFrom().transfer(getTo(), getAmount());
    }
}
