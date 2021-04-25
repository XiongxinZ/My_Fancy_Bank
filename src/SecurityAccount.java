import java.io.Serial;

public class SecurityAccount extends Account{

    public static final String TYPE = "Security";
    @Serial
    private static final long serialVersionUID = 637490753922657801L;

    static int temp = 5000;

    public SecurityAccount(Customer customer, double amount) {
        super(customer, amount);
    }

    public SecurityAccount(Customer customer) {
        super(customer);
    }

    public static String createAccount(Customer customer){
        if (!customer.hasAccount("Saving")){
            return "You should create a Saving Account and put at least $5000 into the account";
        }else if (customer.getAccount("Saving").getAmount() > temp){
            customer.getAccount("Saving").removeCurrency(temp);
            customer.addAccount(TYPE, new LoanAccount(customer));
            return "Pay the fee from Saving Account automatically. Create " + TYPE + " account successfully. ";
        }else{
            return "Your Saving Account should have at least $5000";
        }
    }

    private String transferIn(double val){
        assert getCustomer().getAccount("Saving") != null;
        SavingAccount sa = (SavingAccount) getCustomer().getAccount("Saving");
        if (sa.getAmount() < 5000){
            return "Sorry, you can transfer only when you have more than $5000 " +
                    "in your saving account. Now you only have $" + sa.getAmount();
        }else if (val < 1000){
            return "Sorry, you should transfer at least $1000";
        }else if (sa.getAmount() - val < 2500){
            return "Sorry, after transfer your saving account only have $"
                    + (sa.getAmount() - val) + " remaining. It should remain at least $2500";
        }else{
            sa.removeCurrency(val);
            this.addCurrency(val);
            return "Transfer $" + val + "from Saving account to Security account.\n Saving account: $"
                    +sa.getAmount() + "\n Security account: $" + this.getAmount();
        }
    }
}
