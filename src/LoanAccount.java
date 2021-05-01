import java.io.Serial;

public class LoanAccount extends Account{

    public static final String TYPE = "Loan";
    @Serial
    private static final long serialVersionUid = -864260430747385621L;

    static int temp = 10;

    public LoanAccount(Customer customer, double amount) {
        super(customer, amount, "Loan");
    }

    public LoanAccount(Customer customer) {
        super(customer,"Loan");
    }

    public String repayment(double amount, String cur){
        return new Repayment(this,amount,cur).execute();
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
            customer.markDirty(true);
            AccountDao.insertAccount(loan);
            AccountDao.updateAccountMoney(customer.getAccount("Saving"),"USD");
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully";
        }else{
            return "You can't create Loan Account if you don't have $" + temp + " in your Saving Account";
        }
    }

//    public String requestLoan(Collateral collateral){
//        SystemDatabase.getOrderList().add(new TakeLoan(getCustomer(), collateral));
//        return "Submit application! The collateral worth $"+ collateral.getPrice() +  ". Waiting manager to approve";
//    }

    @Override
    public boolean isMultiCurr() {
        return false;
    }
}
