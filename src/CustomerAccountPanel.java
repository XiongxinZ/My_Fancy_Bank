import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerAccountPanel extends CustomerContentPanel{
    private Account account;
    public CustomerAccountPanel(Account account) {
        super(account.getCustomer());
        this.account = account;
    }

}
