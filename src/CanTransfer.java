public interface CanTransfer {
    String transfer(double val, String accountType);
    String transfer(double val, Account account);
}
