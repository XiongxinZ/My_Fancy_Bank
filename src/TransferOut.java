public class TransferOut extends Transfer{
    public TransferOut(Account from, double amount, String currency) {
        super(from, from.getCustomer().getAccount("Saving"), amount, currency, "Transfer Out");
    }
}
