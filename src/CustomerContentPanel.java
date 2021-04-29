import javax.swing.*;

public class CustomerContentPanel extends JPanel {
    private Customer customer;

    public CustomerContentPanel(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

}
