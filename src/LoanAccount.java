import java.util.HashMap;
import java.util.HashSet;

public class LoanAccount extends Account{

    public static final String TYPE = "Loan";

    private static int temp = 10;

    private ValuePool<Loan> loanPool = new ValuePool<>();
//    private HashMap<String,Double> loanAmount;

    public LoanAccount(Customer customer, double amount) {
        super(customer, amount, "Loan");
    }

    public LoanAccount(Customer customer) {
        super(customer,"Loan");
    }

    @Override
    public boolean canClose() {
        for (String s : SystemDatabase.currType) {
            if (getAmount(s) < 0){
                return false;
            }
        }
        return getCustomer().getAccount("Saving").getAmount() >= 10;
    }

    public String takeLoan(Collateral collateral, String curr){
        return new TakeLoan(this, collateral, curr).execute();
    }

    public static String createAccountFromAccount(Customer customer){
        // only pay from saving account
        if (customer.hasAccount("Saving") && customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").removeCurrency(temp);
            LoanAccount loan = new LoanAccount(customer);
            customer.addAccount(TYPE, loan);
            AccountDao.getInstance().insertAccount(loan);
            AccountDao.getInstance().updateAccountMoney(customer.getAccount("Saving"),"USD");
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            return "You can't create Loan Account if you don't have $" + temp + " in your Saving Account";
        }
    }

    public ValuePool<Loan> getLoanPool() {
        return loanPool;
    }

    public void setLoanPool(ValuePool<Loan> loanPool) {
        this.loanPool = loanPool;
    }

    public HashMap<String, Double> getTotalLoan(){
        return loanPool.calTotal(new ValCounter<Loan>() {
            @Override
            public double getCountedPrice(Loan target) {
                return -target.getValue();
            }
        });
    }

    @Override
    public double getAmount(){
        return loanPool.calTotal(new ValCounter<Loan>() {
            @Override
            public double getCountedPrice(Loan target) {
                return -target.getValue();
            }
        }).get("USD");
    }

    @Override
    public double getAmount(String curr){
        return loanPool.calTotal(new ValCounter<Loan>() {
            @Override
            public double getCountedPrice(Loan target) {
                return -target.getValue();
            }
        }).get(curr);
    }

}
