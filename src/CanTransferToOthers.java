public interface CanTransferToOthers {
    String transfer(Account account, double val,String curr);
    String transfer(String email, String accountType, double val,String curr);
}
